package fabaindaiz.modulator.core.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object ModulatorExecutor: CommandExecutor {

    var executors: MutableMap<String, CommandExecutor> = mutableMapOf()

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        sender.sendMessage(args.joinToString(separator = " "))
        return true
    }

    fun registerCommand() {

    }
}
