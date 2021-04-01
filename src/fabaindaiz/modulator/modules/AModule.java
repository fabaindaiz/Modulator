package fabaindaiz.modulator.modules;

import fabaindaiz.modulator.core.config.langLoader;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;

public abstract class AModule implements IModule {

    private CommandExecutor executor;
    private TabCompleter tab;

    private FileConfiguration config;
    private langLoader lang;
    public String jarName;
    public String name;

    @Override
    public void setJarName(String name) {
        this.jarName = name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void setTabCompleter(TabCompleter tab) {
        this.tab = tab;
    }

    @Override
    public void setConfig(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public void setLang(langLoader lang) {
        this.lang = lang;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public CommandExecutor getExecutor() {
        return executor;
    }

    @Override
    public TabCompleter getTabCompleter() {
        return tab;
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

    @Override
    public langLoader getLang() {
        return lang;
    }

}
