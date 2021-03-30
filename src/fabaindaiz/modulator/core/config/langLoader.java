package fabaindaiz.modulator.core.config;

import fabaindaiz.modulator.Modulator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class langLoader {

    private final FileConfiguration lang;

    public langLoader(FileConfiguration lang) {
        this.lang = lang;
    }

    protected langLoader(Modulator modulator, String fileName) {
        InputStream languageFile = modulator.getResource(fileName);
        Reader configStream = new InputStreamReader(languageFile);
        lang = YamlConfiguration.loadConfiguration(configStream);
    }

    // Retorna una clave de idioma
    public String get(String key) {
        return lang.getString(key, lang.getString("error.nokey"));
    }

    public String get(String parentKey, String key) {
        return lang.getString(parentKey + "." + key, lang.getString("error.nokey"));
    }
}
