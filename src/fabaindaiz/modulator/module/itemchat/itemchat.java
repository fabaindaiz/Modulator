package fabaindaiz.modulator.module.itemchat;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.module.IModule;
import fabaindaiz.modulator.core.config.languageLoader;

public class itemchat implements IModule {
    private final Modulator plugin;
    private languageLoader lang;

    public itemchat(Modulator modulator) {
        this.plugin = modulator;
        this.lang = plugin.getConfiguration().getLang();
    }

    @Override
    public void onEnable() {
        plugin.getCommand("itemchat").setExecutor(new itemchatCommand(plugin));
        plugin.getCommand("itemchat").setTabCompleter(new itemchatTabCompleter(plugin));
        plugin.getCommand("itemchat").setPermissionMessage(lang.get("modulator.error2"));
    }

    @Override
    public void onDisable() { }

}