package fabaindaiz.modulator.modules;

import fabaindaiz.modulator.core.loader.moduleLang;
import org.bukkit.configuration.file.FileConfiguration;

//Interface represent a loadable module
public interface IModule {

    // Enable module
    void onEnable();

    // Disable module
    void onDisable();

    // Jar Name
    void setJarName(String name);

    void setConfig(FileConfiguration config, moduleLang lang);

    FileConfiguration getConfig();

    moduleLang getLang();
}
