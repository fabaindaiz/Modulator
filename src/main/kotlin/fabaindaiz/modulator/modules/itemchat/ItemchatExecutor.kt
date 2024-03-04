package fabaindaiz.modulator.modules.itemchat

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object ItemchatExecutor : CommandExecutor {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        when (label) {
            "itemchat" -> itemchat(sender, args)
        }
        return true
    }

    private fun itemchat(sender: CommandSender, args: Array<out String>) {
        when (args.size) {
            0 -> {
                val player = Bukkit.getPlayer(sender.name)
                if (player != null && player.isOnline) {
                    showItem(sender, player.inventory.itemInMainHand)
                }
            }
            else -> sender.sendMessage("Error en el comando")
        }
    }

}
