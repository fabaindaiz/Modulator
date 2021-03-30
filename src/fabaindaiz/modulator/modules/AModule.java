package fabaindaiz.modulator.modules;

import fabaindaiz.modulator.core.config.langLoader;
import org.bukkit.configuration.file.FileConfiguration;

public abstract class AModule implements IModule {

    private FileConfiguration config;
    private langLoader lang;
    public String jarName;

    @Override
    public void setJarName(String name) {
        this.jarName = name;
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
    public FileConfiguration getConfig() {
        return config;
    }

    @Override
    public langLoader getLang() {
        return lang;
    }

}
