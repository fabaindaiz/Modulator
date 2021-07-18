package fabaindaiz.modulator;

import fabaindaiz.modulator.core.config.Configuration;
import fabaindaiz.modulator.core.depend.libHandler;
import fabaindaiz.modulator.core.depend.sqlHandler;
import fabaindaiz.modulator.core.depend.vaultHandler;
import fabaindaiz.modulator.core.loader.moduleLoader;
import fabaindaiz.modulator.modules.IModule;
import fabaindaiz.modulator.modules.main.modulator;
import fabaindaiz.modulator.modules.modinput.modinput;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Modulator extends JavaPlugin {

    public final LinkedHashMap<String, IModule> commandList = new LinkedHashMap<>();
    private final LinkedHashMap<String, IModule> modules = new LinkedHashMap<>();
    private LinkedList<IModule> sysModules = new LinkedList<IModule>();
    List<String> aliases = new ArrayList<>(Arrays.asList("modulator", "modinput"));

    private IModule modulator;
    private IModule modinput;

    private Configuration configModule;
    private libHandler libHandler;
    private sqlHandler sqlHandler;
    private vaultHandler vaultHandler;
    private moduleLoader moduleLoader;

    @Override
    public void onLoad() {
        libHandler = new libHandler(this);
        libHandler.onEnable();

        // Core modules
        configModule = new Configuration(this);
        sqlHandler = new sqlHandler(this);
        vaultHandler = new vaultHandler(this);
        moduleLoader = new moduleLoader(this);

        sysModules.addAll(Arrays.asList(configModule, sqlHandler, vaultHandler, moduleLoader));
    }

    @Override
    public void onEnable() {
        sysModules.forEach((module) -> module.onEnable());

        modulator = new modulator(this);
        modinput = new modinput(this);

        modules.put("main", modulator);
        modules.put("modinput", modinput);
        commandList.put("modinput", modinput);

        modules.forEach((name, module) -> module.onEnable());
        registerCommand(aliases, modulator);

        Bukkit.getLogger().info(configModule.getMainLang().get("modulator.info1"));
        Bukkit.getLogger().info(configModule.getMainLang().get("modulator.load1"));
    }

    @Override
    public void onDisable() {
        sysModules.forEach((module) -> module.onDisable());
        modules.forEach((name, module) -> module.onDisable());
        modules.clear();

        Bukkit.getLogger().info(configModule.getMainLang().get("modulator.load2"));
    }

    public void reload() {
        configModule.onEnable();
        moduleLoader.onEnable();
        modules.forEach((name, module) -> module.onEnable());
    }

    public void setLoadModules(LinkedHashMap<String, IModule> loadModules) {
        loadModules.forEach((key, value) ->
                this.modules.merge(key, value, (v1, v2) -> v1.equals(v2) ? v1 : v2)
        );
        loadModules.forEach((key, value) -> {
                    this.commandList.merge(value.getName(), value, (v1, v2) -> v1.equals(v2) ? v1 : v2);
                    aliases.add(value.getName());
                }
        );
    }

    public Configuration getConfiguration() {
        return this.configModule;
    }

    public libHandler getLib() {
        return this.libHandler;
    }

    public sqlHandler getSql() {
        return this.sqlHandler;
    }

    public vaultHandler getVault() {
        return this.vaultHandler;
    }

    public Set<String> getModuleNames() {
        return this.modules.keySet();
    }

    private void registerCommand(List<String> aliases, IModule module) {
        Class<?> cl = PluginCommand.class;
        Constructor<?> cons = null;
        PluginCommand plc = null;
        try {
            cons = cl.getDeclaredConstructor(String.class, Plugin.class);
            cons.setAccessible(true);
            plc = (PluginCommand) cons.newInstance("modulator", this);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        plc.setAliases(aliases);
        Bukkit.getCommandMap().register("modulator", plc);
        plc.register(Bukkit.getCommandMap());
        plc.setExecutor(module.getExecutor());
        plc.setTabCompleter(module.getTabCompleter());
        plc.setPermissionMessage(module.getLang().get("error.noper"));
    }

}
