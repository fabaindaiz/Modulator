package fabaindaiz.modulator.modules.modinput;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.modules.AModule;

public class modinput extends AModule {
    private final Modulator plugin;

    public modinput(Modulator modulator) {
        setName("modinput");
        setPermission("modulator.use");

        this.plugin = modulator;
        setLanguageLoader(plugin.getConfiguration().getLanguageLoader());
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
