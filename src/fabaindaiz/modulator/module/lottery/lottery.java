package fabaindaiz.modulator.module.lottery;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.module.IModule;
import fabaindaiz.modulator.core.config.languageLoader;

public class lottery implements IModule {
    private final Modulator plugin;
    private languageLoader lang;

    private lotteryStorage storage;

    public lottery(Modulator modulator) {
        this.plugin = modulator;
        this.lang = plugin.getConfiguration().getLang();
    }

    @Override
    public void onEnable() {
        storage = new lotteryStorage(plugin, lang);
        plugin.getCommand("lottery").setExecutor(new lotteryCommand(plugin, storage));
        plugin.getCommand("lottery").setTabCompleter(new lotteryTabCompleter(plugin));
        plugin.getCommand("lottery").setPermissionMessage(lang.get("modulator.error2"));

        plugin.getServer().getPluginManager().registerEvents(new lotteryListener(plugin, storage), plugin);
    }

    @Override
    public void onDisable() {

    }
}
