import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import fabaindaiz.modulator.modules.AModule;
import fabaindaiz.modulator.core.loader.moduleConfig;

public class democracyMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;
    private languageLoader mainLang;

    // Democracy depends on modinput module
    public democracyMain(Modulator modulator) {
        this.plugin = modulator;
        this.mainLang = plugin.getConfiguration().getMainLang();
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, "democracy", jarName);

        plugin.getCommand("democracy").setExecutor(new democracyCommand(plugin, this));
        plugin.getCommand("democracy").setTabCompleter(new democracyTabCompleter(plugin, this));
        plugin.getCommand("democracy").setPermissionMessage(mainLang.get("modulator.error2"));
    }

    @Override
    public void onDisable() {
    }

}
