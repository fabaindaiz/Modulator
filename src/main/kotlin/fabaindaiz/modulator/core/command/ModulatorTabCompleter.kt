package fabaindaiz.modulator.core.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.util.*

object ModulatorTabCompleter: TabCompleter {

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        val alias = label.lowercase(Locale.getDefault())
        val module = ModulatorCommand.getModule(alias)
        return module?.tabCompleter?.onTabComplete(sender, command, label, args) ?: listOf()
    }

}