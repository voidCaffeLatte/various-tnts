package voidcaffelatte.varioustnts.tnt.generator

import org.bukkit.entity.Entity
import org.bukkit.entity.TNTPrimed
import voidcaffelatte.varioustnts.tnt.ImpactTNT
import voidcaffelatte.varioustnts.tnt.TNT

class ImpactTNTGenerator(
    private val power: Double) : TNTGenerator
{
    override fun generate(tnt: TNTPrimed, owner: Entity?): TNT
    {
        return ImpactTNT(tnt, power)
    }
}