package voidcaffelatte.varioustnts.tnt.generator

import org.bukkit.entity.Entity
import org.bukkit.entity.TNTPrimed
import voidcaffelatte.varioustnts.tnt.TNT

interface TNTGenerator
{
    fun generate(tnt: TNTPrimed, owner: Entity?): TNT
}