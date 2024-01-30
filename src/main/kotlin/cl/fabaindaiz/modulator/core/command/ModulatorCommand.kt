package cl.fabaindaiz.modulator.core.command

import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.command.PluginCommand
import org.bukkit.plugin.Plugin

class ModulatorCommand {

    val pluginCommand: PluginCommand

    init {
        val constructor = PluginCommand::class.java.getDeclaredConstructor(String::class.java, Plugin::class.java)
        constructor.trySetAccessible()

        val commandMapField = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
        commandMapField.trySetAccessible()
        val commandMap = commandMapField.get(Bukkit.getServer()) as CommandMap

        pluginCommand = constructor.newInstance("modulator", this)


        commandMap.register("modulator", pluginCommand)
        pluginCommand.setExecutor(ModulatorExecutor)
        pluginCommand.setTabCompleter(ModulatorTabCompleter)
    }

    fun registerCommand() {
        pluginCommand.setAliases(mutableListOf("mod"))
    }
}