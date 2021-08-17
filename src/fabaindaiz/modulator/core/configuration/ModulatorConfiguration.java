package fabaindaiz.modulator.core.configuration;

import fabaindaiz.modulator.Modulator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

public class ModulatorConfiguration {

    private final Modulator plugin;
    private FileConfiguration configuration;
    private LanguageLoader languageLoader;
    private String lang;

    public ModulatorConfiguration(Modulator plugin) {
        this.plugin = plugin;

        defaultConfiguration();
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public LanguageLoader getLanguageLoader() {
        return languageLoader;
    }

    public String getLang() {
        return lang;
    }

    private void defaultConfiguration() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            plugin.saveResource("config.yml", false);
        }
        configuration = plugin.getConfig();
        lang = configuration.getString("language");

        Reader configurationStream = new InputStreamReader(plugin.getResource("lang" + lang + ".yml"));
        languageLoader = new LanguageLoader(YamlConfiguration.loadConfiguration(configurationStream));
    }

}
