package voidcaffelatte.varioustnts.tnt.generator

import org.bukkit.entity.Entity
import org.bukkit.entity.TNTPrimed
import voidcaffelatte.varioustnts.tnt.ChargeTNT
import voidcaffelatte.varioustnts.tnt.TNT

class ChargeTNTGenerator: TNTGenerator
{
    override fun generate(tnt: TNTPrimed, owner: Entity?): TNT
    {
        return ChargeTNT(tnt)
    }
}