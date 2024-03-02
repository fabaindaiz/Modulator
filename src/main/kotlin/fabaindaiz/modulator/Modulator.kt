package fabaindaiz.modulator

import fabaindaiz.modulator.core.command.ModulatorCommand
import fabaindaiz.modulator.core.module.ModuleLoader
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Modulator: JavaPlugin() {
    companion object {
        var instance: Modulator? = null
            private set
    }

    private val command: ModulatorCommand

    init {
        instance = this
        command = ModulatorCommand
    }

    override fun onEnable() {
        val loader = ModuleLoader(this)
        command.registerLoader(loader)
        command.registerCommand()

        Bukkit.getLogger().info("Enabled!")
    }

    override fun onDisable() {
        command.unregisterLoader()
        command.registerCommand()

        Bukkit.getLogger().info("Disabled!")
    }

}