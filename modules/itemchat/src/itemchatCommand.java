import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.loader.moduleLang;
import fabaindaiz.modulator.modules.IModule;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class itemchatCommand implements CommandExecutor {
    private final boolean norestriction;
    private final boolean enabled;
    private final int collect;
    private final Modulator plugin;
    private final IModule module;
    private final moduleLang lang;
    private boolean usevault;
    private boolean enableOwner = true;

    protected itemchatCommand(Modulator modulator, IModule module) {

        this.plugin = modulator;
        this.module = module;
        this.lang = module.getLang();
        this.norestriction = module.getConfig().getBoolean("itemchat.norestriction");
        this.usevault = module.getConfig().getBoolean("itemchat.usevault");
        this.enabled = module.getConfig().getBoolean("itemchat.enable");
        this.collect = module.getConfig().getInt("itemchat.priceowner");

        if (!plugin.getVault().getServerHasVault()) {
            if (this.usevault) {
                Bukkit.getLogger().warning(lang.get("itemchat.novault1"));
                enableOwner = false;
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!this.enabled) {
            sender.sendMessage(lang.get("itemchat.disable1"));
            return true;
        }
        switch (args.length) {
            case 0:
                sender.sendMessage(lang.get("itemchat.info1"));
                sender.sendMessage(lang.get("itemchat.info2"));
                return true;
            case 1:
                switch (args[0]) {
                    case "help":
                        this.help(sender);
                        return true;
                    case "owner":
                        this.owner(sender);
                        return true;
                    case "show":
                        this.show(sender);
                        return true;
                    default:
                        sender.sendMessage(lang.get("itemchat.error1"));
                        return true;
                }
            default:
                sender.sendMessage(lang.get("itemchat.error1"));
                return true;

        }
    }

    private void help(CommandSender sender) {
        sender.sendMessage(lang.get("itemchat.help1"));
        sender.sendMessage(lang.get("itemchat.help2"));
        sender.sendMessage(lang.get("itemchat.help3"));
    }

    private void owner(CommandSender sender) {
        if (!this.enableOwner) {
            sender.sendMessage(lang.get("itemchat.disable2"));
            return;
        }

        ItemStack item = Bukkit.getPlayer(sender.getName()).getInventory().getItemInMainHand();
        ItemMeta itemMeta = item.getItemMeta();

        if (item.getType().isAir()) {
            sender.sendMessage(lang.get("itemchat.error4"));
            return;
        }
        if (itemMeta.hasLore()) {
            sender.sendMessage(lang.get("itemchat.error3"));
            return;
        }

        List<String> loresList = new ArrayList<>();
        loresList.add(lang.get("itemchat.lore1") + sender.getName());
        itemMeta.setLore(loresList);

        if (this.usevault) {
            EconomyResponse response = plugin.getVault().getEconomy().withdrawPlayer(Bukkit.getPlayer(sender.getName()), this.collect);
            if (!response.transactionSuccess()) {
                sender.sendMessage(lang.get("itemchat.error5"));
                return;
            }
        }

        Bukkit.getPlayer(sender.getName()).getInventory().getItemInMainHand().setItemMeta(itemMeta);
        sender.sendMessage(lang.get("itemchat.owner1"));
        itemchatUtil.showItemSpecs(plugin, sender, item, lang.get("itemchat.interact2"), true);

    }

    private void show(CommandSender sender) {
        ItemStack item = Bukkit.getPlayer(sender.getName()).getInventory().getItemInMainHand();
        if (item.getType().isAir()) {
            sender.sendMessage(lang.get("itemchat.error4"));
            return;
        }

        itemchatUtil.showItemSpecs(plugin, sender, item, lang.get("itemchat.interact1"), norestriction);

    }


}
