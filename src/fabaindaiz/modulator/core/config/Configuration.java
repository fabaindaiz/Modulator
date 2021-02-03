package fabaindaiz.modulator.core.config;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.module.IModule;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;

public class Configuration implements IModule {

    final String[] languages = {"ES"};
    private final Modulator plugin;

    private FileConfiguration mainConfiguration;
    private languageLoader languageConfiguration;


    public Configuration(Modulator modulator) {
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        // Carga el archivo de ajustes
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        this.mainConfiguration = plugin.getConfig();

        // Carga el archivo de idioma
        String lang = mainConfiguration.getString("language");
        lang = Arrays.asList(languages).contains(lang) ? lang : "ES";
        languageConfiguration = new languageLoader(plugin, "language" + lang + ".yml");
    }

    @Override
    public void onDisable() {}

    public FileConfiguration getConfig() {
        return this.mainConfiguration;
    }

    public languageLoader getLang() {
        return this.languageConfiguration;
    }

}
