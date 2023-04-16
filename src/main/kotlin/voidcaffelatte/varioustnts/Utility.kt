package voidcaffelatte.varioustnts

import org.bukkit.GameMode
import org.bukkit.entity.Player

object Utility
{
    fun Player.isGamePlayer(): Boolean
    {
        return this.gameMode == GameMode.SURVIVAL || this.gameMode == GameMode.ADVENTURE;
    }
}