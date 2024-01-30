package cl.fabaindaiz.modulator

import cl.fabaindaiz.modulator.core.command.ModulatorExecutor
import cl.fabaindaiz.modulator.core.command.ModulatorTabCompleter
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandMap
import org.bukkit.command.PluginCommand
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

class Modulator: JavaPlugin() {
    companion object {
        var instance: Modulator? = null
        private set;
    }

    override fun onEnable() {

        instance = this
        Bukkit.getLogger().info("Enabled!")
    }

    override fun onDisable() {
    }


}