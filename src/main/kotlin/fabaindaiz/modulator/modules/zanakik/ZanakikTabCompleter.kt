package fabaindaiz.modulator.modules.zanakik

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

object ZanakikTabCompleter : TabCompleter {

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        TODO("Not yet implemented")
    }

}