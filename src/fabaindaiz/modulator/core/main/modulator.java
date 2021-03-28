package fabaindaiz.modulator.core.main;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import fabaindaiz.modulator.modules.AModule;
import org.bukkit.Bukkit;

public class modulator extends AModule {

    private final Modulator plugin;
    private languageLoader lang;

    public modulator(Modulator modulator) {
        this.plugin = modulator;
        this.lang = plugin.getConfiguration().getMainLang();
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
