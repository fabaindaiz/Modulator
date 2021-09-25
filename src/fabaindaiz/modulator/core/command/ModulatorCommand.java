package fabaindaiz.modulator.core.command;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.loader.ModulatorLoader;
import fabaindaiz.modulator.core.modules.IModule;
import fabaindaiz.modulator.modules.main.modulator;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Represents a class which register all modules and commands for Modulator
 */
public class ModulatorCommand {

    protected final HashMap<String, IModule> modules = new LinkedHashMap<>();
    protected final HashMap<String, IModule> moduleList = new LinkedHashMap<>();

    private final ModulatorLoader modulatorLoader;
    private final ModulatorExecutor modulatorExecutor;
    private final ModulatorTabCompleter modulatorTabCompleter;
    private final Modulator plugin;
    private PluginCommand pluginCommand;

    /**
     * @param plugin Modulator main class
     */
    public ModulatorCommand(Modulator plugin) {

        this.plugin = plugin;
        modulatorLoader = new ModulatorLoader(plugin);
        modulatorExecutor = new ModulatorExecutor(plugin, this);
        modulatorTabCompleter = new ModulatorTabCompleter(plugin, this);

        try {
            Constructor<PluginCommand> constructor = (PluginCommand.class).getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);
            pluginCommand = constructor.newInstance("modulator", plugin);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        registerModule(new modulator(plugin));
    }

    /**
     * Gets modules key set
     * @return A String key set
     */
    public Set<String> getModuleNames() {
        return modules.keySet();
    }

    /**
     * Gets modules lists
     * @return A map with all modules
     */
    public HashMap<String, IModule> getModules() {
        return modules;
    }

    /**
     * Enable all loaded modules
     */
    public void enableModules() {
        File file = new File(plugin.getDataFolder(), "modules");
        modulatorLoader.loadAll(file).forEach((name, module) -> registerModule(module));
        modules.forEach((name, module) -> module.onEnable());
    }

    /**
     * Disable all loaded modules
     */
    public void disableModules() {
        moduleList.forEach((name, module) -> module.onDisable());
    }

    /**
     * Register all aliases for loaded modules
     */
    public void registerPlugin() {
        pluginCommand.setAliases(new ArrayList<>(moduleList.keySet()));
        Bukkit.getCommandMap().register("modulator", pluginCommand);
        pluginCommand.register(Bukkit.getCommandMap());
        pluginCommand.setExecutor(modulatorExecutor);
        pluginCommand.setTabCompleter(modulatorTabCompleter);
    }

    /**
     * Register a user module
     * @param module User module main class
     */
    private void registerModule(IModule module) {
        modules.put(module.getName(), module);
        moduleList.put(module.getName(), module);
        module.getAliases().forEach(alias -> moduleList.put(alias, module));
    }

}
