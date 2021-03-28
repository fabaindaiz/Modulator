package fabaindaiz.modulator.core.config;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.modules.AModule;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;

public class Configuration extends AModule {

    final String[] languages = {"ES"};
    private final Modulator plugin;

    private FileConfiguration mainConfiguration;
    private languageLoader languageConfiguration;
    public String lang;

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
        this.lang = mainConfiguration.getString("language");
        this.lang = Arrays.asList(languages).contains(lang) ? lang : "ES";
        languageConfiguration = new languageLoader(plugin, "lang" + lang + ".yml");
    }

    @Override
    public void onDisable() {
    }

    public FileConfiguration getConfig() {
        return this.mainConfiguration;
    }

    public languageLoader getMainLang() {
        return this.languageConfiguration;
    }

}
