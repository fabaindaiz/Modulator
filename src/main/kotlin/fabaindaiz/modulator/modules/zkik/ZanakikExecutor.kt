package fabaindaiz.modulator.modules.zkik

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object ZanakikExecutor : CommandExecutor {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        when (args[0]) {
            "zkik" -> zanakik(sender, args)
        }

        sender.sendMessage(args.joinToString(separator = " "))
        return true
    }

    private fun zanakik(sender: CommandSender, args: Array<out String>) {
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

}