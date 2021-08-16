package fabaindaiz.modulator.core.modules;

import fabaindaiz.modulator.core.configuration.LanguageLoader;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public interface IModule {

    void onEnable();

    void onDisable();

    void setName(String name);

    void setJarName(String jarName);

    void setVersion(String version);

    void setAliases(List<String> aliases);

    void setExecutor(CommandExecutor executor);

    void setTabCompleter(TabCompleter tabCompleter);

    void setConfiguration(FileConfiguration configuration);

    void setLanguageLoader(LanguageLoader languageLoader);

    String getName();

    String getJarName();

    String getVersion();

    List<String> getAliases();

    CommandExecutor getExecutor();

    TabCompleter getTabCompleter();

    FileConfiguration getConfiguration();

    LanguageLoader getLanguageLoader();

}
