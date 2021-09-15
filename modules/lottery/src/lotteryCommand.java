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

public class lotteryCommand extends CommandDispatcher {
    private final String key = "lottery.command";
    private final boolean usevault;
    private final int price;
    private final int collect;
    private final lotteryStorage storage;

    protected lotteryCommand(Modulator modulator, lotteryStorage storage, IModule module) {
        super(modulator, module);

        this.usevault = module.getConfiguration().getBoolean("lottery.usevault");
        this.price = module.getConfiguration().getInt("lottery.pricelottery");
        this.collect = module.getConfiguration().getInt("lottery.collect");
        storage.setPrice(this.price);
        this.storage = storage;
        setEnabled(module.getConfiguration().getBoolean("lottery.enable"));

        if (!plugin.getDependencies().getVaultHandler().getServerHasVault()) {
            Bukkit.getLogger().warning(lang.get(key, "novault1"));
            if (this.usevault) {
                setEnabled(false);
            }
        }

        register("", this::info);
        register("help", this::help);
        register("buy", this::buy);
        register("draw", this::draw);
        register("collect", this::collect);
        register("results", this::results);

    }

    private boolean info(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        sender.sendMessage(lang.get(key, "info1"));
        sender.sendMessage(lang.get(key, "collect4") + collect);
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
        sender.sendMessage(lang.get(key, "help4"));
        return true;
    }

    private boolean buy(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        String senderName = sender.getName();

        if (!storage.containsPlayer(senderName)) {
            storage.newLottery(sender);
        }

        storage.openInventory(Bukkit.getPlayer(senderName));
        return true;
    }

    private boolean collect(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        ItemStack item = Bukkit.getPlayer(sender.getName()).getInventory().getItemInMainHand();
        List<String> itemLore;
        Boolean isWinner = false;

        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            itemLore = item.getItemMeta().getLore();
            if (itemLore.size() == 2) {
                isWinner = storage.isWinner(itemLore.get(1));
            }
        }

        if (isWinner) {
            EconomyResponse response = plugin.getDependencies().getVaultHandler().getEconomy().depositPlayer(Bukkit.getPlayer(sender.getName()), this.collect);

            if (!response.transactionSuccess()) {
                sender.sendMessage(lang.get(key, "error5"));

                ItemMeta meta = item.getItemMeta();
                meta.getLore().set(1, meta.getLore().get(1) + lang.get(key, "collect3"));

                Bukkit.getPlayer(sender.getName()).getInventory().getItemInMainHand().setItemMeta(meta);
                return true;
            }
            Bukkit.getPlayer(sender.getName()).getInventory().remove(item);
            sender.sendMessage(lang.get(key, "collect1"));

            Bukkit.broadcastMessage(sender.getName() + lang.get(key, "winner1"));
        } else {
            sender.sendMessage(lang.get(key, "collect2"));
        }
        return true;
    }

    private boolean draw(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        if (sender.hasPermission("modulator.adm")) {
            this.storage.drawWinner();
            Bukkit.broadcastMessage(lang.get(key, "draw1"));

            Bukkit.getOnlinePlayers().forEach(player -> results(player));
        }
        sender.sendMessage(lang.get(key, "error.pem"));
        return true;
    }

    private boolean results(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        results(sender);
        return true;
    }

    private void results(CommandSender sender) {
        StringBuilder string = new StringBuilder();
        string.append(lang.get(key, "info4"));

        storage.winner.forEach(number -> {
            string.append(number);
            string.append(" ");
        });
        sender.sendMessage(string.toString());
    }

    protected void noVaultDisable() {
        setEnabled(false);
    }


}
