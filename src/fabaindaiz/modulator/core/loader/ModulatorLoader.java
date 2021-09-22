package fabaindaiz.modulator.core.loader;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.modules.IModule;

import java.io.File;
import java.util.HashMap;

/**
 * Represents a class which can load modules from jar files
 */
public class ModulatorLoader {

    private final Modulator plugin;

    /**
     * @param plugin Modulator main class
     */
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

    /**
     * Load all jar modules from a directory
     * @param dir Module files main directory
     * @return A map with all modules
     */
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
