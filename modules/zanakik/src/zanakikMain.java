import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.ModuleConfiguration;
import fabaindaiz.modulator.core.modules.AModule;

import java.util.Arrays;

public class zanakikMain extends AModule {
    private final Modulator plugin;
    private ModuleConfiguration moduleConfiguration;

    public zanakikMain(Modulator modulator) {
        setName("zanakik");
        setDescription("\u00A7e/zanakik \u00A7fZanakikea a jugadores");
        setPermission("modulator.op");

        setAliases(Arrays.asList("zkik"));

        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfiguration = new ModuleConfiguration(plugin, this, getName(), getJarName());

        setExecutor(new zanakikCommand(plugin, this));
        setTabCompleter(new zanakikTabCompleter(plugin, this));

        plugin.getServer().getPluginManager().registerEvents(new zanakikListener(plugin, this), plugin);
    }

    @Override
    public void onDisable() {
    }

}