import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import fabaindaiz.modulator.modules.AModule;

public class bettorMain extends AModule {
    private final Modulator plugin;
    private languageLoader lang;

    public bettorMain(Modulator modulator) {
        this.plugin = modulator;
        this.lang = plugin.getConfiguration().getMainLang();
    }

    @Override
    public void onEnable() {
        plugin.getCommand("bettor").setExecutor(new bettorCommand(plugin));
        plugin.getCommand("bettor").setTabCompleter(new bettorTabCompleter(plugin));
        plugin.getCommand("bettor").setPermissionMessage(lang.get("modulator.error2"));
    }

    @Override
    public void onDisable() {

    }
}
