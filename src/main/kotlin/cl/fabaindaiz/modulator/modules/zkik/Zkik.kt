package cl.fabaindaiz.modulator.modules.zkik

import cl.fabaindaiz.modulator.core.module.Module
import org.bukkit.command.CommandExecutor
import org.bukkit.command.TabCompleter

class Zkik (
    override val name: String = "Zkik",
    override val description: String = "",
    override val version: String = "1.0",
    override val aliases: List<String> = listOf("zkik"),
    override val executor: CommandExecutor = ZkikExecutor,
    override val tabCompleter: TabCompleter = TODO()
) : Module {

    override fun onEnable() {
    }

    override fun onDisable() {
    }
}