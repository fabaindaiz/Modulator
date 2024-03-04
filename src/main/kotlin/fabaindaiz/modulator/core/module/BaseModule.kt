package fabaindaiz.modulator.core.module

import org.bukkit.command.CommandExecutor
import org.bukkit.command.TabCompleter

interface BaseModule {

    val name: String
    val description: String
    val aliases: List<String>

    val executor: CommandExecutor
    val tabCompleter: TabCompleter
    val configuration: Configuration

    fun onEnable()
    fun onDisable()

}
