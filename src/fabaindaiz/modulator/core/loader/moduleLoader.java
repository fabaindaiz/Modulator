package fabaindaiz.modulator.core.loader;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.modules.AModule;
import fabaindaiz.modulator.modules.IModule;

import java.io.File;
import java.util.LinkedHashMap;

public class moduleLoader extends AModule {
    private final Modulator plugin;

    public moduleLoader(Modulator modulator) {
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        File file = new File(plugin.getDataFolder(), "config");
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(plugin.getDataFolder(), "modules");
        if (!file.exists()) {
            file.mkdir();
        }
        plugin.setLoadModules(loadAll(file));
    }

    @Override
    public void onDisable() {

    }

    public LinkedHashMap<String, IModule> loadAll(File dir) {
        if (!dir.isDirectory() || !dir.exists()) {
            return new LinkedHashMap<>();
        }
        LinkedHashMap<String, IModule> loaded = new LinkedHashMap<>();
        jarLoader loader = new jarLoader();
        for (File file : dir.listFiles((file, name) -> name.endsWith(".jar"))) {
            String name = file.getName().replace(".jar", "");
            loaded.put(name, loader.load(file, IModule.class, this.plugin));
        }
        loaded.forEach((name, module) -> module.setJarName(name));
        return loaded;
    }
}