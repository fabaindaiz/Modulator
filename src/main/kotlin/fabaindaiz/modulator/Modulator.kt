package fabaindaiz.modulator

import fabaindaiz.modulator.core.command.ModulatorCommand
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Modulator: JavaPlugin() {
    companion object {
        var instance: Modulator? = null
        private set;
    }

    val command = ModulatorCommand()

    override fun onEnable() {
        val command =  ModulatorCommand()

        instance = this
        Bukkit.getLogger().info("Enabled!")
    }

    override fun onDisable() {
    }

}