package fabaindaiz.modulator.modules;

import fabaindaiz.modulator.core.config.langLoader;
import org.bukkit.configuration.file.FileConfiguration;

//Interface represent a loadable module
public interface IModule {

    // Enable module
    void onEnable();

    // Disable module
    void onDisable();

    // Jar Name
    void setJarName(String name);

    void setConfig(FileConfiguration config);

    void setLang(langLoader lang);

    FileConfiguration getConfig();

    langLoader getLang();
}
