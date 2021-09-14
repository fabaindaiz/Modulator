package fabaindaiz.modulator.core.modules;

import fabaindaiz.modulator.core.configuration.LanguageLoader;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public abstract class AModule implements IModule {

    private String name;
    private String description;
    private String jarName;
    private String version;
    private List<String> aliases = new ArrayList<>();
    private CommandExecutor executor;
    private TabCompleter tabCompleter;
    private FileConfiguration configuration;
    private LanguageLoader languageLoader;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getJarName() {
        return jarName;
    }

    @Override
    public void setJarName(String jarName) {
        this.jarName = jarName;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    @Override
    public CommandExecutor getExecutor() {
        return executor;
    }

    @Override
    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    @Override
    public TabCompleter getTabCompleter() {
        return tabCompleter;
    }

    @Override
    public void setTabCompleter(TabCompleter tabCompleter) {
        this.tabCompleter = tabCompleter;
    }

    @Override
    public FileConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public void setConfiguration(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public LanguageLoader getLanguageLoader() {
        return languageLoader;
    }

    @Override
    public void setLanguageLoader(LanguageLoader languageLoader) {
        this.languageLoader = languageLoader;
    }

}
