package cl.fabaindaiz.modulator.modules.zkik

import cl.fabaindaiz.modulator.Modulator
import org.apache.commons.lang.ObjectUtils.Null
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

object ZkikExecutor : CommandExecutor {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        when (args[0]) {
            "zkik" -> zkik(sender, args)
        }

        sender.sendMessage(args.joinToString(separator = " "))
        return true
    }

    fun zkik(sender: CommandSender, args: Array<out String>) {
        when (args.size) {
            1 -> {
                val player = Bukkit.getOnlinePlayers().random()
                kikPlayer(player, 1)
            }
            2 -> {
                val player = Bukkit.getPlayer(args[1])
                if (player != null && player.isOnline) {
                    kikPlayer(player, 1)
                }
            }
            else -> sender.sendMessage("Error en el comando")
        }
    }

    fun kikPlayer(player: Player, num: Int) {
        player.inventory.addItem(getZanakik(num))
        Bukkit.broadcastMessage("$player será zanakikeadx.")
        Bukkit.getScheduler().scheduleSyncDelayedTask(Modulator.instance as Plugin, fun () = player.kickPlayer("Zanakik"), 300L)
    }

    fun getZanakik(num: Int): ItemStack {
        val item = ItemStack(Material.GOLDEN_APPLE, num)
        item.itemMeta?.setDisplayName("&6Zanakik")
        item.itemMeta?.setCustomModelData(1)
        item.itemMeta?.lore = listOf("Esto es un Zanakik.")
        return item
    }

}