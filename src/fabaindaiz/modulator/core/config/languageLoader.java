package fabaindaiz.modulator.core.config;

import fabaindaiz.modulator.Modulator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class languageLoader {

    private final FileConfiguration languageConfig;

    // Carga un archivo de idiomas
    protected languageLoader(Modulator modulator, String fileName) {
        InputStream languageFile = modulator.getResource(fileName);
        Reader configStream = new InputStreamReader(languageFile);
        languageConfig = YamlConfiguration.loadConfiguration(configStream);
    }

    // Retorna una clave de idioma
    public String get(String key) {
        return languageConfig.getString(key, languageConfig.getString("modulator.error3"));
    }
}
