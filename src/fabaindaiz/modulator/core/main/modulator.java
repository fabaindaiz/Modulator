package fabaindaiz.modulator.core.main;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.module.IModule;
import fabaindaiz.modulator.core.config.languageLoader;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public class modulator implements IModule {

    private final Modulator plugin;
    private languageLoader lang;

    public modulator(Modulator modulator) {
        this.plugin = modulator;
        this.lang = plugin.getConfiguration().getLang();
    }

    @Override
    public void onEnable() {
        plugin.getCommand("modulator").setExecutor(new modulatorCommand(plugin));
        plugin.getCommand("modulator").setTabCompleter(new modulatorTabCompleter(plugin));
        Bukkit.getLogger().info(lang.get("modulator.error2"));
        plugin.getCommand("modulator").setPermissionMessage(lang.get("modulator.error2"));
    }

    @Override
    public void onDisable() {

    }
}
