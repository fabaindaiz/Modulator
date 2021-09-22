package fabaindaiz.modulator.core.modules;

import fabaindaiz.modulator.core.configuration.LanguageLoader;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

/**
 * Represents a interface which can be a module to load with Modulator
 */
public interface IModule {

    /**
     * Called when this module is enabled
     */
    void onEnable();

    /**
     * Called when this module is disabled
     */
    void onDisable();

    /**
     * Gets module name
     * @return Module name
     */
    String getName();

    /**
     * Sets module name
     * @param name Module name
     */
    void setName(String name);

    /**
     * Gets module description
     * @return Module description
     */
    String getDescription();

    /**
     * Sets module description
     * @param description Module description
     */
    void setDescription(String description);

    /**
     * Gets module jar name
     * @return Module jar name
     */
    String getJarName();

    /**
     * Sets module jar name
     * @param jarName Module jar name
     */
    void setJarName(String jarName);

    /**
     * Gets module version
     * @return Module version
     */
    String getVersion();

    /**
     * Sets module version
     * @param version Module version
     */
    void setVersion(String version);

    /**
     * Gets module main permission
     * @return Module main permission
     */
    String getPermission();

    /**
     * Sets module main permission
     * @param permission Module main permission
     */
    void setPermission(String permission);

    /**
     * Gets module aliases
     * @return Module aliases
     */
    List<String> getAliases();

    /**
     * Sets module aliases
     * @param aliases Module aliases
     */
    void setAliases(List<String> aliases);

    /**
     * Gets module main command executor
     * @return Module main command executor
     */
    CommandExecutor getExecutor();

    /**
     * Sets module main command executor
     * @param executor Module main command executor
     */
    void setExecutor(CommandExecutor executor);

    /**
     * Gets module main tab completer executor
     * @return Module main tab completer executor
     */
    TabCompleter getTabCompleter();

    /**
     * Sets module main tab completer
     * @param tabCompleter Module main tab completer
     */
    void setTabCompleter(TabCompleter tabCompleter);

    /**
     * Gets module main configuration
     * @return Module main configuration
     */
    FileConfiguration getConfiguration();

    /**
     * Sets module main configuration
     * @param configuration Module main configuration
     */
    void setConfiguration(FileConfiguration configuration);

    /**
     * Gets module main language configuration
     * @return Module main language configuration
     */
    LanguageLoader getLanguageLoader();

    /**
     * Sets module main language configuration
     * @param languageLoader Module main language configuration
     */
    void setLanguageLoader(LanguageLoader languageLoader);

}
