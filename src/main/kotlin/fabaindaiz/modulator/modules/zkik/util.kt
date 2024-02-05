package fabaindaiz.modulator.modules.zkik

import fabaindaiz.modulator.Modulator
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

fun kikPlayer(player: Player, num: Int) {
    player.inventory.addItem(getZanakik(num))
    Bukkit.broadcastMessage("$player será zanakikeadx.")
    Bukkit.getScheduler().scheduleSyncDelayedTask(Modulator.instance as Plugin, fun () = player.kickPlayer("Zanakik"), 300L)
}

private fun getZanakik(num: Int): ItemStack {
    val item = ItemStack(Material.GOLDEN_APPLE, num)
    item.itemMeta?.setDisplayName("&6Zanakik")
    item.itemMeta?.setCustomModelData(1)
    item.itemMeta?.lore = listOf("Esto es un Zanakik.")
    return item
}