package fabaindaiz.modulator.module.modinput;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.module.IModule;
import fabaindaiz.modulator.core.config.languageLoader;

public class modinput implements IModule {
    private final Modulator plugin;
    private languageLoader lang;

    public modinput(Modulator modulator) {

        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        this.lang = plugin.getConfiguration().getLang();
        plugin.getCommand("modinput").setExecutor(new modinputCommand(plugin));
        plugin.getCommand("modinput").setTabCompleter(new modinputTabCompleter(plugin));
        plugin.getCommand("modinput").setPermissionMessage(lang.get("modulator.error2"));
    }

    @Override
    public void onDisable() {

    }
}
