import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dispatcher.CommandDispatcher;
import fabaindaiz.modulator.core.modules.IModule;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class itemchatCommand extends CommandDispatcher {
    private final boolean norestriction;
    private final int collect;
    private final String key = "itemchat.command";
    private boolean usevault;
    private boolean enableOwner = true;

    protected itemchatCommand(Modulator modulator, IModule module) {
        super(modulator, module);

        this.norestriction = module.getConfiguration().getBoolean("itemchat.norestriction");
        this.usevault = module.getConfiguration().getBoolean("itemchat.usevault");
        this.collect = module.getConfiguration().getInt("itemchat.priceowner");
        setEnabled(module.getConfiguration().getBoolean("itemchat.enable"));

        if (!plugin.getDependencies().getVaultHandler().getServerHasVault()) {
            if (this.usevault) {
                Bukkit.getLogger().warning(lang.get(key, "novault1"));
                enableOwner = false;
            }
        }

        register("", this::info);
        register("help", this::help);
        register("show", this::show);
        register("owner", this::owner);
    }

    private boolean info(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        sender.sendMessage(lang.get(key, "info1"));
        sender.sendMessage(lang.get(key, "info2"));
        return true;
    }

    private boolean help(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        sender.sendMessage(lang.get(key, "help1"));
        sender.sendMessage(lang.get(key, "help2"));
        sender.sendMessage(lang.get(key, "help3"));
        return true;
    }

    private boolean show(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        ItemStack item = Bukkit.getPlayer(sender.getName()).getInventory().getItemInMainHand();
        if (item.getType().isAir()) {
            sender.sendMessage(lang.get(key, "error4"));
            return true;
        }

        itemchatUtil.showItemSpecs(module, sender, item, lang.get(key, "interact1"), norestriction);
        return true;
    }

    private boolean owner(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        if (!this.enableOwner) {
            sender.sendMessage(lang.get(key, "disable2"));
            return true;
        }

        ItemStack item = Bukkit.getPlayer(sender.getName()).getInventory().getItemInMainHand();
        ItemMeta itemMeta = item.getItemMeta();

        if (item.getType().isAir()) {
            sender.sendMessage(lang.get(key, "error4"));
            return true;
        }
        if (itemMeta.hasLore()) {
            sender.sendMessage(lang.get(key, "error3"));
            return true;
        }

        List<String> loresList = new ArrayList<>();
        loresList.add(lang.get(key, "lore1") + sender.getName());
        itemMeta.setLore(loresList);

        if (this.usevault) {
            EconomyResponse response = plugin.getDependencies().getVaultHandler().getEconomy().withdrawPlayer(Bukkit.getPlayer(sender.getName()), this.collect);
            if (!response.transactionSuccess()) {
                sender.sendMessage(lang.get(key, "error5"));
                return true;
            }
        }

        Bukkit.getPlayer(sender.getName()).getInventory().getItemInMainHand().setItemMeta(itemMeta);
        sender.sendMessage(lang.get(key, "owner1"));
        itemchatUtil.showItemSpecs(module, sender, item, lang.get(key, "interact2"), true);

        return true;
    }

}
