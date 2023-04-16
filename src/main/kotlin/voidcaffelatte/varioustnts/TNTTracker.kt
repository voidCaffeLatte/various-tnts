package voidcaffelatte.varioustnts

import org.bukkit.entity.EntityType
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityExplodeEvent
import voidcaffelatte.varioustnts.tnt.TNT

class TNTTracker(
    plugin: VariousTNTs) : Listener
{
    private val tnts: HashMap<String, TNT> = hashMapOf()

    init
    {
        plugin.server.pluginManager.registerEvents(this, plugin);
    }

    fun add(tnt: TNT)
    {
        tnts[tnt.id] = tnt
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private fun onEntityExploded(event: EntityExplodeEvent)
    {
        if (event.entity !is TNTPrimed) return
        val uid = event.entity.uniqueId.toString()
        tnts[uid]?.onExploded(event)
        tnts.remove(uid)
    }

    @EventHandler
    private fun onEntityDamaged(event: EntityDamageByEntityEvent)
    {
        if (event.damager.type != EntityType.PRIMED_TNT) return
        tnts[event.damager.uniqueId.toString()]?.onDamagedEntity(event)
    }
}