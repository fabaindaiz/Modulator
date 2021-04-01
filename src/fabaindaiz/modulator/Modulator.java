package fabaindaiz.modulator;

import fabaindaiz.modulator.core.config.Configuration;
import fabaindaiz.modulator.core.loader.moduleLoader;
import fabaindaiz.modulator.core.main.modulator;
import fabaindaiz.modulator.core.modvault.vaultHandler;
import fabaindaiz.modulator.modules.IModule;
import fabaindaiz.modulator.modules.modinput.modinput;
import fabaindaiz.modulator.modules.modtask.modtask;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.*;

public class Modulator extends JavaPlugin {

    List<String> aliases = new ArrayList<>(Arrays.asList("modulator","md","modinput","modtask"));
    public final LinkedHashMap<String, IModule> commandList = new LinkedHashMap<>();
    private final LinkedHashMap<String, IModule> modules = new LinkedHashMap<>();

    private Configuration configModule;
    private vaultHandler vaultHandler;
    private moduleLoader moduleLoader;
    private static CommandMap cMap;

    @Override
    public void onLoad() {
        // Core modules
        configModule = new Configuration(this);
        vaultHandler = new vaultHandler(this);
        moduleLoader = new moduleLoader(this);
        configModule.onEnable();
        moduleLoader.onEnable();
    }

    @Override
    public void onEnable() {
        IModule modulator = new modulator(this);
        IModule modinput = new modinput(this);
        IModule modtask = new modtask(this);

        modules.put("main", modulator);
        modules.put("modinput", modinput);
        modules.put("modtask", modtask);

        commandList.put("modinput", modinput);
        commandList.put("modtask", modtask);

        modules.forEach((name, module) -> module.onEnable());
        getCommand("modulator").setAliases(aliases);

        Bukkit.getLogger().info(configModule.getMainLang().get("modulator.info1"));
        Bukkit.getLogger().info(configModule.getMainLang().get("modulator.load1"));
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(configModule.getMainLang().get("modulator.load2"));
        modules.forEach((name, module) -> module.onDisable());
        modules.clear();
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

    public vaultHandler getVault() {
        return this.vaultHandler;
    }

    public Set<String> getModuleNames() {
        return this.modules.keySet();
    }

    private void setupCommandMap() {
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            cMap = (CommandMap) field.get(Bukkit.getServer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerCommand(Command command) {
        cMap.register(command.getName(), command);
    }

}