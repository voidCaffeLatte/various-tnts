package voidcaffelatte.varioustnts.tnt

import org.bukkit.entity.TNTPrimed
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityExplodeEvent

class ChargeTNT(
    tnt: TNTPrimed) : TNT
{
    override val id = tnt.uniqueId.toString()

    override fun onExploded(event: EntityExplodeEvent)
    {
        event.blockList().clear()
    }

    override fun onDamagedEntity(event: EntityDamageByEntityEvent)
    {
        event.isCancelled = true
    }
}