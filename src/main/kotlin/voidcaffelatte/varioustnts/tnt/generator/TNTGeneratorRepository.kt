package voidcaffelatte.varioustnts.tnt.generator

class TNTGeneratorRepository
{
    // TODO: Load generators from a config file
    private val tntGenerators: HashMap<String, TNTGenerator> = hashMapOf(
        "Charge" to ChargeTNTGenerator(),
        "Ammunition" to AmmunitionTNTGenerator(5.0),
        "Impact" to ImpactTNTGenerator(3.0))

    fun get(id: String): TNTGenerator?
    {
        return tntGenerators[id]
    }

    fun contains(id: String): Boolean
    {
        return tntGenerators.containsKey(id)
    }
}