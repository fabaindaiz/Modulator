package fabaindaiz.modulator.core.loader;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import fabaindaiz.modulator.modules.IModule;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class moduleConfig {

    final String[] languages = {"ES"};

    private final Modulator plugin;
    private final IModule module;

    private String pluginName;
    private String jarName;

    public moduleConfig(Modulator modulator, IModule module, String pluginName, String jarName) {
        this.pluginName = pluginName;
        this.module = module;
        this.jarName = jarName;
        this.plugin = modulator;
        loadConfig();
    }

    public void loadConfig() {
        // Carga el archivo de ajustes
        String lang = plugin.getConfiguration().lang;
        File file = new File(plugin.getDataFolder(), "config/"+pluginName);
        if (!file.exists()) {
            file.mkdir();
        }

        File configFile = new File(plugin.getDataFolder(), "config/"+pluginName+"/config.yml");
        File langFile = new File(plugin.getDataFolder(), "config/"+pluginName+"/lang"+lang+".yml");
        if(!configFile.exists() || !langFile.exists()) {
            try {
                JarFile jar = new JarFile(new File(plugin.getDataFolder(), "modules/"+jarName+".jar"));
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.isDirectory() || !entry.getName().endsWith(".yml")) {
                        continue;
                    }
                    File tempFile = new File(plugin.getDataFolder(), "config/"+pluginName+"/"+entry.getName());
                    InputStream inputStream = jar.getInputStream(entry);
                    OutputStream outputStream = new FileOutputStream(tempFile);
                    IOUtils.copy(inputStream, outputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            InputStream configStream = new FileInputStream(configFile);
            Reader configReader = new InputStreamReader(configStream);

            InputStream langStream = new FileInputStream(langFile);
            Reader langReader = new InputStreamReader(langStream);

            FileConfiguration tempConf = YamlConfiguration.loadConfiguration(configReader);
            moduleLang tempLang = new moduleLang(YamlConfiguration.loadConfiguration(langReader));

            module.setConfig(tempConf, tempLang);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

