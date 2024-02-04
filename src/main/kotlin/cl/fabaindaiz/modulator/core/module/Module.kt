package cl.fabaindaiz.modulator.core.module

import org.bukkit.command.CommandExecutor
import org.bukkit.command.TabCompleter

interface Module {

    val name: String
    val description: String
    val version: String
    val aliases: List<String>
    val executor: CommandExecutor
    val tabCompleter: TabCompleter

    fun onEnable()

    fun onDisable()
}