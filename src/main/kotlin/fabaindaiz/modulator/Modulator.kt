package fabaindaiz.modulator

import fabaindaiz.modulator.core.command.ModulatorCommand
import fabaindaiz.modulator.core.module.ModuleLoader
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Modulator: JavaPlugin() {
    companion object {
        var instance: Modulator? = null
        private set;
    }

    private val command = ModulatorCommand(this)

    override fun onEnable() {
        instance = this

        val loader = ModuleLoader(this)
        command.registerLoader(loader)
        command.registerCommand()

        Bukkit.getLogger().info("Enabled!")
    }

    override fun onDisable() {
        instance = null

        Bukkit.getLogger().info("Disabled!")
    }

}