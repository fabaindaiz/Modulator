package cl.fabaindaiz.modulator

import cl.fabaindaiz.modulator.core.command.ModulatorCommand
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Modulator: JavaPlugin() {
    companion object {
        var instance: Modulator? = null
        private set;
    }

    override fun onEnable() {
        val command =  ModulatorCommand()

        instance = this
        Bukkit.getLogger().info("Enabled!")
    }

    override fun onDisable() {
    }


}