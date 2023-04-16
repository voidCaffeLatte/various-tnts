package voidcaffelatte.varioustnts

import org.bukkit.plugin.java.JavaPlugin
import voidcaffelatte.varioustnts.tnt.generator.TNTGeneratorRepository

class VariousTNTs : JavaPlugin()
{
    private lateinit var tntGeneratorRepository: TNTGeneratorRepository
    private lateinit var dispenserTracker: DispenserTracker
    private lateinit var tntBlockTracker: TNTBlockTracker
    private lateinit var tntTracker: TNTTracker

    override fun onEnable()
    {
        tntGeneratorRepository = TNTGeneratorRepository()
        dispenserTracker = DispenserTracker(this)
        tntTracker = TNTTracker(this)
        tntBlockTracker = TNTBlockTracker(this, dispenserTracker, tntTracker, tntGeneratorRepository)
    }

    override fun onDisable()
    {
    }
}
