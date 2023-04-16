package voidcaffelatte.varioustnts

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.data.type.Dispenser
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.*
import org.bukkit.inventory.ItemStack
import voidcaffelatte.varioustnts.Utility.isGamePlayer
import voidcaffelatte.varioustnts.tnt.generator.TNTGeneratorRepository

class TNTBlockTracker(
    plugin: VariousTNTs,
    private val dispenserTracker: DispenserTracker,
    private val tntTracker: TNTTracker,
    private val tntGeneratorRepository: TNTGeneratorRepository) : Listener
{
    private val tntBlocks: HashMap<BlockPosition, ArrayDeque<TNTBlock>> = hashMapOf()

    init
    {
        plugin.server.pluginManager.registerEvents(this, plugin);
    }

    @EventHandler
    private fun onEntityAdded(event: EntityAddToWorldEvent)
    {
        val entity = event.entity
        if (entity !is TNTPrimed) return

        val block = entity.location.block
        val location = block.location
        val tntBlock = popTNTBlock(location.toBlockPosition()) ?: return

        val tntGenerator = tntGeneratorRepository.get(tntBlock.tntID) ?: return
        tntTracker.add(tntGenerator.generate(entity, tntBlock.owner))
    }

    @EventHandler
    private fun onBlockPlaced(event: BlockPlaceEvent)
    {
        if (event.itemInHand.type != Material.TNT) return

        val id = event.itemInHand.getVTId() ?: return
        if (!tntGeneratorRepository.contains(id)) return

        val item = event.itemInHand.clone().also { it.amount = 1 }
        val placedLocation = event.blockPlaced.location
        val placedPosition = placedLocation.toBlockPosition()

        val tntBlock = TNTBlock(id, item, event.player)
        pushTNTBlock(placedPosition, tntBlock)
    }

    @EventHandler
    private fun onBlockDispense(event: BlockDispenseEvent)
    {
        if (event.item.type != Material.TNT) return

        val id = event.item.getVTId() ?: return
        if (!tntGeneratorRepository.contains(id)) return

        val item = event.item.clone().also { it.amount = 1 }
        val dispenser = event.block.blockData as Dispenser
        val dispensedBlock = event.block.getRelative(dispenser.facing)
        val dispensedBlockPosition = dispensedBlock.location.toBlockPosition()
        val dispenserPosition = event.block.location.toBlockPosition()
        val owner = dispenserTracker.getOwner(dispenserPosition)

        val tntBlock = TNTBlock(id, item, owner)
        pushTNTBlock(dispensedBlockPosition, tntBlock)
    }

    @EventHandler
    private fun onPistonExtend(event: BlockPistonExtendEvent)
    {
        moveTNTBlocks(event.blocks.filter { it.type == Material.TNT }, event.direction)
    }

    @EventHandler
    private fun onPistonRetract(event: BlockPistonRetractEvent)
    {
        if (!event.isSticky) return
        moveTNTBlocks(event.blocks.filter { it.type == Material.TNT }, event.direction)
    }

    @EventHandler
    private fun onBlockBroken(event: BlockBreakEvent)
    {
        if (event.block.type != Material.TNT) return
        if (!event.player.isGamePlayer()) return

        val location = event.block.location
        val tntBlock = popTNTBlock(location.toBlockPosition()) ?: return
        event.isDropItems = false
        event.block.world.dropItem(event.block.location, tntBlock.item)
    }

    private fun moveTNTBlocks(blocks: List<Block>, direction: BlockFace)
    {
        val moveInfos = ArrayList<Pair<Block, TNTBlock>>(blocks.size)
        for (block in blocks)
        {
            val tntBlock = popTNTBlock(block.location.toBlockPosition()) ?: continue
            moveInfos.add(Pair(block, tntBlock))
        }

        for (info in moveInfos)
        {
            val block = info.first
            val movedBlock = block.getRelative(direction)
            val tntBlock = info.second
            pushTNTBlock(movedBlock.location.toBlockPosition(), tntBlock)
        }
    }

    private fun pushTNTBlock(position: BlockPosition, tntBlock: TNTBlock)
    {
        tntBlocks.getOrPut(position) { ArrayDeque() }.add(tntBlock)
    }

    private fun popTNTBlock(position: BlockPosition): TNTBlock?
    {
        val tnts = tntBlocks[position] ?: return null
        if (tnts.isEmpty()) return null
        return tnts.removeLast()
    }

    companion object
    {
        private const val TAG_PREFIX: String = "[VT]"

        private fun ItemStack.getVTId(): String?
        {
            val loreTexts = this.itemMeta.lore ?: return null
            val tag = loreTexts.firstOrNull { it.startsWith(TAG_PREFIX, true) } ?: return null
            return tag.split(" ").getOrNull(1)
        }
    }
}