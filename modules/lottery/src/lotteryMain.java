import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import fabaindaiz.modulator.modules.AModule;
import fabaindaiz.modulator.core.loader.moduleConfig;

public class lotteryMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;
    private languageLoader mainLang;

    private lotteryStorage storage;

    public lotteryMain(Modulator modulator) {
        this.plugin = modulator;
        this.mainLang = plugin.getConfiguration().getMainLang();
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, "lottery", jarName);

        storage = new lotteryStorage(plugin, getLang(), this);
        plugin.getCommand("lottery").setExecutor(new lotteryCommand(plugin, storage, this));
        plugin.getCommand("lottery").setTabCompleter(new lotteryTabCompleter(plugin, this));
        plugin.getCommand("lottery").setPermissionMessage(mainLang.get("modulator.error2"));

        plugin.getServer().getPluginManager().registerEvents(new lotteryListener(plugin, storage, this), plugin);
    }

    @Override
    public void onDisable() {

    }
}
