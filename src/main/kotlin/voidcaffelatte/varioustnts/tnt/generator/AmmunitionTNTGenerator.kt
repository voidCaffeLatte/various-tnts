package voidcaffelatte.varioustnts.tnt.generator

import org.bukkit.entity.Entity
import org.bukkit.entity.TNTPrimed
import voidcaffelatte.varioustnts.tnt.AmmunitionTNT
import voidcaffelatte.varioustnts.tnt.TNT

class AmmunitionTNTGenerator(
    private val requiredTravelDistanceToExplode: Double) : TNTGenerator
{
    override fun generate(tnt: TNTPrimed, owner: Entity?): TNT
    {
        return AmmunitionTNT(tnt, owner, requiredTravelDistanceToExplode)
    }
}