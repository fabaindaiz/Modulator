import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import fabaindaiz.modulator.modules.AModule;

public class testMain extends AModule {
    private final Modulator plugin;
    private languageLoader lang;

    public testMain(Modulator modulator) {
        this.plugin = modulator;
        this.lang = plugin.getConfiguration().getMainLang();
    }

    @Override
    public void onEnable() {
        plugin.getCommand("test").setExecutor(new testCommand(plugin));
        plugin.getCommand("test").setTabCompleter(new testTabCompleter(plugin));
        plugin.getCommand("test").setPermissionMessage(lang.get("modulator.error2"));
    }

    @Override
    public void onDisable() {

    }
}