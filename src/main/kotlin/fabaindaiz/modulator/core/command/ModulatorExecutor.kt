package fabaindaiz.modulator.core.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.util.*

object ModulatorExecutor: CommandExecutor {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val alias = label.lowercase(Locale.getDefault())
        val module = ModulatorCommand.getModule(alias)
        return module?.executor?.onCommand(sender, command, label, args) ?: true
    }

}
