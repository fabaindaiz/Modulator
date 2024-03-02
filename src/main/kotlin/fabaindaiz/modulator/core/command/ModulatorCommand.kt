package fabaindaiz.modulator.core.command

import fabaindaiz.modulator.Modulator
import fabaindaiz.modulator.core.module.BaseModule
import fabaindaiz.modulator.core.module.ModuleLoader
import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.command.PluginCommand
import org.bukkit.plugin.Plugin

class ModulatorCommand (val modulator: Modulator) {
    private val pluginCommand: PluginCommand
    private val commandMap: CommandMap

    private val modules = mutableListOf<BaseModule>()

    init {
        val commandMapField = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
        commandMapField.trySetAccessible()
        commandMap = commandMapField.get(Bukkit.getServer()) as CommandMap

        val constructor = PluginCommand::class.java.getDeclaredConstructor(String::class.java, Plugin::class.java)
        constructor.trySetAccessible()
        pluginCommand = constructor.newInstance("modulator", modulator)
    }

    fun registerModule(module: BaseModule) {
        modules.add(module)
    }

    fun registerLoader(loader: ModuleLoader) {
        modules.addAll(loader.modules)
    }

    fun registerCommand() {
        val aliases = modules.flatMap { it.aliases }.toMutableList()

        pluginCommand.setAliases(aliases)
        pluginCommand.setExecutor(ModulatorExecutor)
        pluginCommand.setTabCompleter(ModulatorTabCompleter)
        commandMap.register("modulator", pluginCommand)
    }

}