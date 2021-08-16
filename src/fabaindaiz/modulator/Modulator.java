package fabaindaiz.modulator;

import fabaindaiz.modulator.core.command.ModulatorCommand;
import fabaindaiz.modulator.core.configuration.ModulatorConfiguration;
import fabaindaiz.modulator.core.loader.ModulatorDependencies;
import org.bukkit.plugin.java.JavaPlugin;

public class Modulator extends JavaPlugin {

    ModulatorConfiguration modulatorConfiguration;
    ModulatorDependencies modulatorDependencies;
    ModulatorCommand modulatorCommand;

    @Override
    public void onEnable() {
        modulatorConfiguration = new ModulatorConfiguration(this);
        modulatorDependencies = new ModulatorDependencies(this);
        modulatorCommand = new ModulatorCommand(this);

        modulatorCommand.enableModules();
        modulatorCommand.registerPlugin();
    }

    @Override
    public void onDisable() {
        modulatorCommand.disableModules();
    }

    public void reload() {
        onDisable();
        onEnable();
    }

    public ModulatorConfiguration getConfiguration() {
        return modulatorConfiguration;
    }

    public ModulatorDependencies getDependencies() {
        return modulatorDependencies;
    }

    public ModulatorCommand getCommand() {
        return modulatorCommand;
    }


}
