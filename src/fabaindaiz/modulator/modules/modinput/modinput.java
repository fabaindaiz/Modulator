package fabaindaiz.modulator.modules.modinput;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.modules.AModule;

public class modinput extends AModule {
    private final Modulator plugin;

    public modinput(Modulator modulator) {
        this.plugin = modulator;
        setLang(plugin.getConfiguration().getMainLang());
    }

    @Override
    public void onEnable() {
        plugin.getCommand("modinput").setExecutor(new modinputCommand(plugin, this));
        plugin.getCommand("modinput").setTabCompleter(new modinputTabCompleter(plugin, this));
        plugin.getCommand("modinput").setPermissionMessage(getLang().get("error.noper"));
    }

    @Override
    public void onDisable() {

    }
}
