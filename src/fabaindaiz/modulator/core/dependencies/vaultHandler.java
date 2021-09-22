package fabaindaiz.modulator.core.dependencies;

import fabaindaiz.modulator.Modulator;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * Represents a class which manage vault economy operations
 */
public class vaultHandler {

    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;
    private final Modulator plugin;
    private final boolean serverHasVault;

    /**
     * @param plugin Modulator main class
     */
    public vaultHandler(Modulator plugin) {
        this.plugin = plugin;

        if (!setupEconomy()) {
            serverHasVault = false;
            return;
        }
        serverHasVault = true;
        setupPermissions();
        setupChat();
    }

    /**
     * Gets vault economy for Modulator
     * @return Vault economy
     */
    public static Economy getEconomy() {
        return econ;
    }

    /**
     * Gets vault permissions for Modulator
     * @return Vault permission
     */
    public static Permission getPermissions() {
        return perms;
    }

    /**
     * Gets vault chat for Modulator
     * @return Vault chat
     */
    public static Chat getChat() {
        return chat;
    }

    /**
     * Deposit money to player
     * @param player Target player
     * @param amount Transaction amount
     * @return true if successful transaction
     */
    public boolean depositPlayer(Player player, float amount) {
        if (!serverHasVault) {
            return false;
        }
        EconomyResponse r = econ.depositPlayer(player, amount);
        return r.transactionSuccess();
    }

    /**
     * Withdraw money from player
     * @param player Target player
     * @param amount Transaction amount
     * @return true if successful transaction
     */
    public boolean withdrawPlayer(Player player, float amount) {
        if (!serverHasVault) {
            return false;
        }
        EconomyResponse r = econ.withdrawPlayer(player, amount);
        return r.transactionSuccess();
    }

    /**
     * Setup vault economy for Modulator
     * @return true if setup economy
     */
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

    /**
     * Setup vault permissions for Modulator
     * @return true if setup permissions
     */
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return true;
    }

    /**
     * Setup vault chat for Modulator
     * @return true if chat setup
     */
    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return true;
    }

    /**
     * Gets server vault status
     * @return true if server has vault
     */
    public boolean getServerHasVault() {
        return serverHasVault;
    }

}
