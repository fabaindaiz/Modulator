package fabaindaiz.modulator.modules.zanakik

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
        when (label) {
            "zkik" -> zanakik(sender, args)
        }
        return true
    }

    private fun zanakik(sender: CommandSender, args: Array<out String>) {
        when (args.size) {
            0 -> {
                val player = Bukkit.getOnlinePlayers().randomOrNull()
                if (player != null && player.isOnline) {
                    kikPlayer(player, 1)
                } else {
                    sender.sendMessage("No se pudo zanakikear")
                }
            }
            1 -> {
                val player = Bukkit.getPlayer(args[0])
                if (player != null && player.isOnline) {
                    kikPlayer(player, 1)
                } else {
                    sender.sendMessage("No se pudo zanakikear")
                }
            }
            else -> sender.sendMessage("Error en el comando")
        }
    }

}