package fabaindaiz.modulator.core.modules;

import fabaindaiz.modulator.core.configuration.LanguageLoader;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public abstract class AModule implements IModule {

    private String name;
    private String jarName;
    private String version;
    private List<String> aliases;
    private CommandExecutor executor;
    private TabCompleter tabCompleter;
    private FileConfiguration configuration;
    private LanguageLoader languageLoader;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setJarName(String jarName) {
        this.jarName = jarName;
    }
    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    @Override
    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void setTabCompleter(TabCompleter tabCompleter) {
        this.tabCompleter = tabCompleter;
    }

    @Override
    public void setConfiguration(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void setLanguageLoader(LanguageLoader languageLoader) {
        this.languageLoader = languageLoader;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getJarName() {
        return jarName;
    }

    @Override
    public String  getVersion() {
        return version;
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public CommandExecutor getExecutor() {
       return executor;
    }

    @Override
    public TabCompleter getTabCompleter() {
        return tabCompleter;
    }

    @Override
    public FileConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public LanguageLoader getLanguageLoader() {
        return languageLoader;
    }

}
