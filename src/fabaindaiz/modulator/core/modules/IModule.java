package fabaindaiz.modulator.core.modules;

import fabaindaiz.modulator.core.configuration.LanguageLoader;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public interface IModule {

    void onEnable();

    void onDisable();

    String getName();

    void setName(String name);

    String getJarName();

    void setJarName(String jarName);

    String getVersion();

    void setVersion(String version);

    List<String> getAliases();

    void setAliases(List<String> aliases);

    CommandExecutor getExecutor();

    void setExecutor(CommandExecutor executor);

    TabCompleter getTabCompleter();

    void setTabCompleter(TabCompleter tabCompleter);

    FileConfiguration getConfiguration();

    void setConfiguration(FileConfiguration configuration);

    LanguageLoader getLanguageLoader();

    void setLanguageLoader(LanguageLoader languageLoader);

}
