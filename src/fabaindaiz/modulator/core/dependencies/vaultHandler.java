package fabaindaiz.modulator.core.dependencies;

import fabaindaiz.modulator.Modulator;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class vaultHandler {

    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;
    private final Modulator plugin;
    private final boolean serverHasVault;

    public vaultHandler(Modulator modulator) {

        this.plugin = modulator;

        if (!setupEconomy()) {
            serverHasVault = false;
            return;
        }
        serverHasVault = true;
        setupPermissions();
        setupChat();
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }

    public static Chat getChat() {
        return chat;
    }

    public boolean depositPlayer(Player player, float amount) {
        if (!serverHasVault) {
            return false;
        }
        EconomyResponse r = econ.depositPlayer(player, amount);
        return r.transactionSuccess();
    }

    public boolean withdrawPlayer(Player player, float amount) {
        if (!serverHasVault) {
            return false;
        }
        EconomyResponse r = econ.withdrawPlayer(player, amount);
        return r.transactionSuccess();
    }

    private boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return true;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return true;
    }

    public boolean getServerHasVault() {
        return serverHasVault;
    }

}
