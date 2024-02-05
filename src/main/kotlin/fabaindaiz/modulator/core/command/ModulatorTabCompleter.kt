package fabaindaiz.modulator.core.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

object ModulatorTabCompleter: TabCompleter {

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        TODO("Not yet implemented")
    }
}
