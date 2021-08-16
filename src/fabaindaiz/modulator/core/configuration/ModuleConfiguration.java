package fabaindaiz.modulator.core.configuration;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.modules.IModule;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleConfiguration {

    private String name;
    private String jarName;

    private final Modulator plugin;
    private final IModule module;

    public ModuleConfiguration(Modulator plugin, IModule module, String name, String jarName) {
        this.name = name;
        this.jarName = jarName;

        this.plugin = plugin;
        this.module = module;

        loadConfiguration();
    }

    private void loadConfiguration() {
        String lang = plugin.getConfiguration().getLang();
        File file = new File(plugin.getDataFolder(), "config/" + name);
        if (!file.exists()) {
            file.mkdir();
        }

        File configuration = new File(plugin.getDataFolder(), "config/" + name + "/config.yml");
        File language = new File(plugin.getDataFolder(), "config/" + name + "/lang" + lang + ".yml");
        if (!configuration.exists() || !language.exists()) {
            try {
                JarFile jar = new JarFile(new File(plugin.getDataFolder(), "modules/" + jarName + ".jar"));
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.isDirectory() || !entry.getName().endsWith(".yml")) {
                        continue;
                    }
                    File tempFile = new File(plugin.getDataFolder(), "config/" + name + "/" + entry.getName());
                    IOUtils.copy(jar.getInputStream(entry), new FileOutputStream(tempFile));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Reader configurationReader = new InputStreamReader(new FileInputStream(configuration));
            Reader languageReader = new InputStreamReader(new FileInputStream(language));

            module.setConfiguration(YamlConfiguration.loadConfiguration(configurationReader));
            module.setLanguageLoader(new LanguageLoader(YamlConfiguration.loadConfiguration(languageReader)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
