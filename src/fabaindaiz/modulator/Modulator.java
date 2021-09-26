package fabaindaiz.modulator;

import fabaindaiz.modulator.core.command.ModulatorCommand;
import fabaindaiz.modulator.core.configuration.ModulatorConfiguration;
import fabaindaiz.modulator.core.handler.ModulatorException;
import fabaindaiz.modulator.core.loader.ModulatorDependencies;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Represents the Modulator main class
 */
public class Modulator extends JavaPlugin {

    ModulatorConfiguration modulatorConfiguration;
    ModulatorDependencies modulatorDependencies;
    ModulatorCommand modulatorCommand;

    /**
     * Called when this plugin is enabled
     */
    @Override
    public void onEnable() {
        try {
            modulatorConfiguration = new ModulatorConfiguration(this);
            modulatorDependencies = new ModulatorDependencies(this);
            modulatorCommand = new ModulatorCommand(this);

            modulatorCommand.enableModules();
            modulatorCommand.registerPlugin();
        } catch (Exception e) {
            throw new ModulatorException.ModulatorLoadException(e);
        }
    }

    /**
     * Called when this plugin is disabled
     */
    @Override
    public void onDisable() {
        modulatorCommand.disableModules();
    }

    /**
     * Called when this plugin is reloaded
     */
    public void reload() {
        onDisable();
        onEnable();
    }

    /**
     * Gets modulator main configuration loader
     * @return Modulator main configuration
     */
    public ModulatorConfiguration getConfiguration() {
        return modulatorConfiguration;
    }

    /**
     * Gets modulator dependencies utilities
     * @return Modulator dependencies
     */
    public ModulatorDependencies getDependencies() {
        return modulatorDependencies;
    }

    /**
     * Gets modulator module and command manager
     * @return Modulator module loader
     */
    public ModulatorCommand getCommand() {
        return modulatorCommand;
    }

}
