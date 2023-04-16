package voidcaffelatte.varioustnts

import org.bukkit.Location

data class BlockPosition(val x: Int, val y: Int, val z: Int)

fun Location.toBlockPosition(): BlockPosition
{
    return BlockPosition(this.blockX, this.blockY, this.blockZ)
}
