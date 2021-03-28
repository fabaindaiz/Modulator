package fabaindaiz.modulator.modules;

import fabaindaiz.modulator.core.loader.moduleLang;
import org.bukkit.configuration.file.FileConfiguration;

public abstract class AModule implements IModule{

    private FileConfiguration config;
    private moduleLang lang;
    public String jarName;

    @Override
    public void setJarName(String name) {
        this.jarName = name;
    }

    @Override
    public void setConfig(FileConfiguration config, moduleLang lang) {
        this.config = config;
        this.lang = lang;
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

    @Override
    public moduleLang getLang() {
        return lang;
    }

}
