package fabaindaiz.modulator.modules;

import fabaindaiz.modulator.core.config.langLoader;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;

//Interface represent a loadable module
public interface IModule {

    // Enable module
    void onEnable();

    // Disable module
    void onDisable();

    // Jar Name
    void setJarName(String name);

    void setName(String name);

    void setExecutor(CommandExecutor executor);

    void setTabCompleter(TabCompleter tab);

    void setConfig(FileConfiguration config);

    void setLang(langLoader lang);

    String getName();

    CommandExecutor getExecutor();

    TabCompleter getTabCompleter();

    FileConfiguration getConfig();

    langLoader getLang();




}
