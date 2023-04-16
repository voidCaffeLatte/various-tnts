package voidcaffelatte.varioustnts

import org.bukkit.entity.Entity
import org.bukkit.inventory.ItemStack

data class TNTBlock(
    var tntID: String,
    var item: ItemStack,
    var owner: Entity? = null)
