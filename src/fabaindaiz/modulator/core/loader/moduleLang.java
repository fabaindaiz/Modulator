package fabaindaiz.modulator.core.loader;

import org.bukkit.configuration.file.FileConfiguration;

public class moduleLang {

    private final FileConfiguration lang;

    // Carga un archivo de idiomas
    protected moduleLang(FileConfiguration lang) {
        this.lang = lang;
    }

    // Retorna una clave de idioma
    public String get(String key) {
        return lang.getString(key, lang.getString("error"));
    }
}
