package fabaindaiz.modulator.core.configuration;

import fabaindaiz.modulator.Modulator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Represents a class which load the Modulator main configuration
 */
public class ModulatorConfiguration {

    private final Modulator plugin;
    private FileConfiguration configuration;
    private LanguageLoader languageLoader;
    private String lang;

    /**
     * @param plugin Modulator main class
     */
    public ModulatorConfiguration(Modulator plugin) {
        this.plugin = plugin;
        defaultConfiguration();
    }

    /**
     * Gets the Modulator main configuration
     * @return Main configuration file
     */
    public FileConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Gets the Modulator main language configuration
     * @return Main language file
     */
    public LanguageLoader getLanguageLoader() {
        return languageLoader;
    }

    /**
     * Gets the current language
     * @return A string which represent current language
     */
    public String getLang() {
        return lang;
    }

    /**
     * Prepare all default configuration files
     */
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
