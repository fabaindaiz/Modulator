import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.langLoader;
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
    private final langLoader lang;
    private final String key = "itemchat.command";
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
                Bukkit.getLogger().warning(lang.get(key, "novault1"));
                enableOwner = false;
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!this.enabled) {
            sender.sendMessage(lang.get(key, "disable1"));
            return true;
        }
        switch (args.length) {
            case 0:
                sender.sendMessage(lang.get(key, "info1"));
                sender.sendMessage(lang.get(key, "info2"));
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
                        sender.sendMessage(lang.get(key, "error1"));
                        return true;
                }
            default:
                sender.sendMessage(lang.get(key, "error1"));
                return true;

        }
    }

    private void help(CommandSender sender) {
        sender.sendMessage(lang.get(key, "help1"));
        sender.sendMessage(lang.get(key, "help2"));
        sender.sendMessage(lang.get(key, "help3"));
    }

    private void owner(CommandSender sender) {
        if (!this.enableOwner) {
            sender.sendMessage(lang.get(key, "disable2"));
            return;
        }

        ItemStack item = Bukkit.getPlayer(sender.getName()).getInventory().getItemInMainHand();
        ItemMeta itemMeta = item.getItemMeta();

        if (item.getType().isAir()) {
            sender.sendMessage(lang.get(key, "error4"));
            return;
        }
        if (itemMeta.hasLore()) {
            sender.sendMessage(lang.get(key, "error3"));
            return;
        }

        List<String> loresList = new ArrayList<>();
        loresList.add(lang.get(key, "lore1") + sender.getName());
        itemMeta.setLore(loresList);

        if (this.usevault) {
            EconomyResponse response = plugin.getVault().getEconomy().withdrawPlayer(Bukkit.getPlayer(sender.getName()), this.collect);
            if (!response.transactionSuccess()) {
                sender.sendMessage(lang.get(key, "error5"));
                return;
            }
        }

        Bukkit.getPlayer(sender.getName()).getInventory().getItemInMainHand().setItemMeta(itemMeta);
        sender.sendMessage(lang.get(key, "owner1"));
        itemchatUtil.showItemSpecs(module, sender, item, lang.get(key, "interact2"), true);

    }

    private void show(CommandSender sender) {
        ItemStack item = Bukkit.getPlayer(sender.getName()).getInventory().getItemInMainHand();
        if (item.getType().isAir()) {
            sender.sendMessage(lang.get(key, "error4"));
            return;
        }

        itemchatUtil.showItemSpecs(module, sender, item, lang.get(key, "interact1"), norestriction);

    }


}
