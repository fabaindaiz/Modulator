package fabaindaiz.modulator.core.configuration;

import org.bukkit.configuration.file.FileConfiguration;

public class LanguageLoader {

    private final FileConfiguration language;

    public LanguageLoader(FileConfiguration language) {
        this.language = language;
    }

    public String get(String key) {
        return language.getString(key, language.getString("error.nokey"));
    }

    public String get(String parent, String key) {
        return language.getString(parent + "." + key, language.getString("error.nokey"));
    }
}
