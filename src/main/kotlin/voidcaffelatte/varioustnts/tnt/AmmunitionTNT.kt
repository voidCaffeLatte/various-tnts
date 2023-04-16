package voidcaffelatte.varioustnts.tnt

import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityExplodeEvent

class AmmunitionTNT(
    private val tnt: TNTPrimed,
    private val tntOwner: Entity?,
    private val requiredTravelDistanceToExplode: Double) : TNT
{
    private val originalLocation = tnt.location.clone()

    override val id = tnt.uniqueId.toString()

    override fun onExploded(event: EntityExplodeEvent)
    {
        if (isMovedEnough()) return
        event.blockList().clear()
    }

    override fun onDamagedEntity(event: EntityDamageByEntityEvent)
    {
        val tntTeam =
            if (tntOwner == null || tntOwner !is Player) null else tntOwner.scoreboard.getEntryTeam(tntOwner.name)
        val entity = event.entity
        val entityTeam = if (entity !is Player) null else entity.scoreboard.getEntryTeam(entity.name)
        event.isCancelled = !isMovedEnough()
            || (tntTeam != null && entityTeam != null && tntTeam.name == entityTeam.name)
    }

    private fun isMovedEnough(): Boolean
    {
        val currentPosition = this.tnt.location.clone().also { it.y = 0.0 }
        return currentPosition.distance(originalLocation) >= requiredTravelDistanceToExplode
    }
}