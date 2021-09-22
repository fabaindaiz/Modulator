package fabaindaiz.modulator.core.configuration;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Represents a class which represent a language configuration
 */
public class LanguageLoader {

    private final FileConfiguration language;

    /**
     * @param language Language configuration file
     */
    public LanguageLoader(FileConfiguration language) {
        this.language = language;
    }

    /**
     * Gets a string from a language file represented by a key
     * @param key The pathname string
     * @return A string from language file
     */
    public String get(String key) {
        return language.getString(key, language.getString("error.nokey"));
    }

    /**
     * Gets a string from a language file represented by a key
     * @param parent The parent abstract pathname
     * @param key The pathname string
     * @return A string from language file
     */
    public String get(String parent, String key) {
        String path = parent + "." + key;
        return language.getString(path, language.getString("error.nokey") + path);
    }

}
