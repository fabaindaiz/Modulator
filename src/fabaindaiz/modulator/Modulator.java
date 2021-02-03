package fabaindaiz.modulator;

import fabaindaiz.modulator.module.IModule;
import fabaindaiz.modulator.core.config.Configuration;
import fabaindaiz.modulator.core.modvault.vaultHandler;
import fabaindaiz.modulator.core.main.modulator;
import fabaindaiz.modulator.module.modinput.modinput;
import fabaindaiz.modulator.module.modtask.modtask;
import fabaindaiz.modulator.module.bettor.bettor;
import fabaindaiz.modulator.module.democracy.democracy;
import fabaindaiz.modulator.module.itemchat.itemchat;
import fabaindaiz.modulator.module.lottery.lottery;
import fabaindaiz.modulator.module.zanakik.zanakik;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.LinkedHashMap;
import java.util.Set;

public class Modulator extends JavaPlugin {

    private final LinkedHashMap<String, IModule> modules = new LinkedHashMap<>();
    private Configuration configModule;
    private vaultHandler vaultHandler;

    @Override
    public void onLoad() {
        // Core modules
        configModule = new Configuration(this);
        vaultHandler = new vaultHandler(this);
        configModule.onEnable();
        modules.put("Main", new modulator(this));
    }

    @Override
    public void onEnable() {
        //  Util modules
        modules.put("ModInput", new modinput(this));
        modules.put("ModTask", new modtask(this));

        // Custom modules
        // modules.put("Bettor", new bettor(this));
        modules.put("Democracy", new democracy(this));
        modules.put("ItemChat", new itemchat(this));
        modules.put("Lottery", new lottery(this));
        modules.put("Zanakik", new zanakik(this));

        modules.forEach((name, module) -> module.onEnable());

        Bukkit.getLogger().info(configModule.getLang().get("modulator.info1"));
        Bukkit.getLogger().info(configModule.getLang().get("modulator.load1"));
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(configModule.getLang().get("modulator.load2"));
        modules.forEach((name, module) -> module.onDisable());
        modules.clear();
    }

    public void reload() {
        configModule.onEnable();
        modules.forEach((name, module) -> module.onEnable());
    }

    public Configuration getConfiguration() {
        return this.configModule;
    }

    public vaultHandler getVault() {
        return this.vaultHandler;
    }

    public Set<String> getModuleNames() {
        return this.modules.keySet();
    }

}