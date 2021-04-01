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
        setExecutor(new modinputCommand(plugin, this));
        setTabCompleter(new modinputTabCompleter(plugin, this));
    }

    @Override
    public void onDisable() {

    }
}
