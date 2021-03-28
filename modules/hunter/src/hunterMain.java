import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import fabaindaiz.modulator.modules.AModule;

public class hunterMain extends AModule {
    private final Modulator plugin;
    private languageLoader lang;

    public hunterMain(Modulator modulator) {

        this.plugin = modulator;
        this.lang = plugin.getConfiguration().getMainLang();
    }

    @Override
    public void onEnable() {
        plugin.getCommand("hunter").setExecutor(new hunterCommand(plugin));
        plugin.getCommand("hunter").setTabCompleter(new hunterTabCompleter(plugin));
        plugin.getCommand("hunter").setPermissionMessage(lang.get("modulator.error2"));
    }

    @Override
    public void onDisable() {

    }
}