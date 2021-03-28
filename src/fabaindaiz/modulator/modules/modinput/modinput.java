package fabaindaiz.modulator.modules.modinput;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import fabaindaiz.modulator.modules.AModule;

public class modinput extends AModule {
    private final Modulator plugin;
    private languageLoader lang;

    public modinput(Modulator modulator) {

        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        this.lang = plugin.getConfiguration().getMainLang();
        plugin.getCommand("modinput").setExecutor(new modinputCommand(plugin));
        plugin.getCommand("modinput").setTabCompleter(new modinputTabCompleter(plugin));
        plugin.getCommand("modinput").setPermissionMessage(lang.get("modulator.error2"));
    }

    @Override
    public void onDisable() {

    }
}
