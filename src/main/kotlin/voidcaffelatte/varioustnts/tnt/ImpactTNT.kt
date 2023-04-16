package voidcaffelatte.varioustnts.tnt

import org.bukkit.entity.TNTPrimed
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityExplodeEvent

class ImpactTNT(
    tnt: TNTPrimed,
    private val power: Double) : TNT
{
    override val id = tnt.uniqueId.toString()

    override fun onExploded(event: EntityExplodeEvent)
    {
        event.blockList().clear()
    }

    override fun onDamagedEntity(event: EntityDamageByEntityEvent)
    {
        val entity = event.entity
        val tnt = event.damager

        val velocity = entity.location.subtract(tnt.location).toVector().normalize().multiply(power)
        entity.velocity = entity.velocity.add(velocity)

        event.isCancelled = true
    }
}