package fabaindaiz.modulator.modules;

import fabaindaiz.modulator.core.config.langLoader;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;

public interface IModule {

    void onEnable();

    void onDisable();

    void setJarName(String name);

    String getName();

    void setName(String name);

    CommandExecutor getExecutor();

    void setExecutor(CommandExecutor executor);

    TabCompleter getTabCompleter();

    void setTabCompleter(TabCompleter tab);

    FileConfiguration getConfig();

    void setConfig(FileConfiguration config);

    langLoader getLang();

    void setLang(langLoader lang);

}
