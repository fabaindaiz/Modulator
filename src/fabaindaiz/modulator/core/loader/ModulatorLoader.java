package fabaindaiz.modulator.core.loader;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.modules.IModule;

import java.io.File;
import java.util.HashMap;

public class ModulatorLoader {

    private final Modulator plugin;

    public ModulatorLoader(Modulator plugin) {
        this.plugin = plugin;

        File file = new File(plugin.getDataFolder(), "config");
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(plugin.getDataFolder(), "modules");
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public HashMap<String, IModule> loadAll(File dir) {
        HashMap<String, IModule> modules = new HashMap<>();
        if (!dir.isDirectory() || !dir.exists()) {
            return modules;
        }
        ModuleJarLoader jarLoader = new ModuleJarLoader();
        for (File file : dir.listFiles((file, name) -> name.endsWith(".jar"))) {
            String name = file.getName().replace(".jar", "");
            modules.put(name, jarLoader.load(file, IModule.class, plugin));
        }
        modules.forEach((name, module) -> module.setJarName(name));
        return modules;
    }

}
