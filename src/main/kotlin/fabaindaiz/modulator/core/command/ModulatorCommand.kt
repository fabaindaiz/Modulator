package fabaindaiz.modulator.core.command

import fabaindaiz.modulator.Modulator
import fabaindaiz.modulator.modules.zkik.ZanakikExecutor
import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.command.PluginCommand
import org.bukkit.plugin.Plugin

class ModulatorCommand {

    private val pluginCommand: PluginCommand
    private val commandMap: CommandMap

    init {
        val commandMapField = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
        commandMapField.trySetAccessible()
        commandMap = commandMapField.get(Bukkit.getServer()) as CommandMap

        val constructor = PluginCommand::class.java.getDeclaredConstructor(String::class.java, Plugin::class.java)
        constructor.trySetAccessible()
        pluginCommand = constructor.newInstance("modulator", this)
    }

    fun registerCommand() {
        pluginCommand.setAliases(mutableListOf("zkik"))
        commandMap.register("modulator", pluginCommand)
        pluginCommand.setExecutor(ZanakikExecutor)
        pluginCommand.setTabCompleter(ModulatorTabCompleter)
    }
}