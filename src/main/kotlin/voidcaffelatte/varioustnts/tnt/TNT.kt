package voidcaffelatte.varioustnts.tnt

import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityExplodeEvent

interface TNT
{
    val id: String
    fun onExploded(event: EntityExplodeEvent)
    fun onDamagedEntity(event: EntityDamageByEntityEvent)
}