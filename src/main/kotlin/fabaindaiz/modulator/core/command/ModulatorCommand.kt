package fabaindaiz.modulator.core.command

import fabaindaiz.modulator.Modulator
import fabaindaiz.modulator.core.module.BaseModule
import fabaindaiz.modulator.core.module.ModuleLoader
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandMap
import org.bukkit.command.PluginCommand
import org.bukkit.plugin.Plugin

object ModulatorCommand {
    private val pluginCommand: PluginCommand
    private val commandMap: CommandMap

    private val modules = mutableListOf<BaseModule>()
    private val aliases = mutableMapOf<String, BaseModule>()

    init {
        val commandMapField = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
        commandMapField.trySetAccessible()
        commandMap = commandMapField.get(Bukkit.getServer()) as CommandMap

        val constructor = PluginCommand::class.java.getDeclaredConstructor(String::class.java, Plugin::class.java)
        constructor.trySetAccessible()
        pluginCommand = constructor.newInstance("modulator", Modulator.instance)

        pluginCommand.setExecutor(ModulatorExecutor)
        pluginCommand.setTabCompleter(ModulatorTabCompleter)
    }

    fun registerLoader(loader: ModuleLoader) {
        loader.modules.forEach { module ->
            module.aliases.forEach { alias ->
                aliases.putIfAbsent(alias, module)
            }
        }
        modules.addAll(loader.modules)
    }

    fun unregisterLoader() {
        modules.clear()
    }

    fun registerCommand() {
        pluginCommand.setAliases(modules.flatMap { it.aliases }.toMutableList())
        commandMap.register("modulator", pluginCommand)
    }

    fun getModule(alias: String) : BaseModule? {
        return aliases[alias]
    }

}