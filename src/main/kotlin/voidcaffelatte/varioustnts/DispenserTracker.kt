package voidcaffelatte.varioustnts

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityExplodeEvent

class DispenserTracker(
    plugin: VariousTNTs) : Listener
{
    private val dispensers: HashMap<BlockPosition, BlockInfo> = hashMapOf()

    init
    {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    fun getOwner(blockPosition: BlockPosition): Player?
    {
        return dispensers[blockPosition]?.owner
    }

    @EventHandler
    fun onBlockPlaced(event: BlockPlaceEvent)
    {
        val itemInHand = event.itemInHand
        if (itemInHand.type != Material.DISPENSER) return

        val location = event.blockPlaced.location
        val position = BlockPosition(location.blockX, location.blockY, location.blockZ)

        val blockInfo = BlockInfo(event.player)
        dispensers[position] = blockInfo
    }

    @EventHandler
    fun onBlockBroken(event: BlockBreakEvent)
    {
        if (event.block.type != Material.DISPENSER) return
        val location = event.block.location
        val position = BlockPosition(location.blockX, location.blockY, location.blockZ)
        dispensers.remove(position)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onEntityExploded(event: EntityExplodeEvent)
    {
        event.blockList().asSequence().filter { it.type == Material.DISPENSER }.forEach()
        {
            val location = it.location
            val position = BlockPosition(location.blockX, location.blockY, location.blockZ)
            dispensers.remove(position)
        }
    }

    companion object
    {
        private data class BlockInfo(val owner: Player)
    }
}