import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.LanguageLoader;
import fabaindaiz.modulator.core.modules.IModule;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class lotteryCommand implements CommandExecutor {
    private final Modulator plugin;
    private final IModule module;
    private final LanguageLoader lang;
    private final String key = "lottery.command";
    private final boolean usevault;
    private final int price;
    private final int collect;
    private boolean enabled;
    private final lotteryStorage storage;

    protected lotteryCommand(Modulator modulator, lotteryStorage storage, IModule module) {

        this.plugin = modulator;
        this.module = module;
        this.lang = module.getLanguageLoader();
        this.enabled = module.getConfiguration().getBoolean("lottery.enable");
        this.usevault = module.getConfiguration().getBoolean("lottery.usevault");
        this.price = module.getConfiguration().getInt("lottery.pricelottery");
        this.collect = module.getConfiguration().getInt("lottery.collect");
        this.storage = storage;

        storage.setPrice(this.price);
        if (!plugin.getDependencies().getVaultHandler().getServerHasVault()) {
            Bukkit.getLogger().warning(lang.get(key, "novault1"));
            if (this.usevault) {
                this.enabled = false;
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!this.enabled) {
            sender.sendMessage(lang.get(key, "disable1"));
            return true;
        }
        if (!sender.hasPermission("modulator.lottery")){
            sender.sendMessage(lang.get("error.noper"));
            return true;
        }

        switch (args.length) {
            case 0:
                sender.sendMessage(lang.get(key, "info1"));
                sender.sendMessage(lang.get(key, "collect4") + collect);
                sender.sendMessage(lang.get(key, "info2"));
                return true;
            case 1:
                switch (args[0]) {
                    case "help":
                        this.help(sender);
                        return true;
                    case "buy":
                        this.buy(sender);
                        return true;
                    case "draw":
                        this.draw(sender);
                        return true;
                    case "collect":
                        this.collect(sender);
                        return true;
                    case "results":
                        this.results(sender);
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
        sender.sendMessage(lang.get(key, "help4"));
    }

    private void buy(CommandSender sender) {
        String senderName = sender.getName();

        if (!storage.containsPlayer(senderName)) {
            storage.newLottery(sender);
        }

        storage.openInventory(Bukkit.getPlayer(senderName));
    }

    private void collect(CommandSender sender) {
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
                return;
            }
            Bukkit.getPlayer(sender.getName()).getInventory().remove(item);
            sender.sendMessage(lang.get(key, "collect1"));

            Bukkit.broadcastMessage(sender.getName() + lang.get(key, "winner1"));
        } else {
            sender.sendMessage(lang.get(key, "collect2"));
        }
    }

    private void draw(CommandSender sender) {
        if (sender.hasPermission("modulator.lotteryDraw")) {
            this.storage.drawWinner();
            Bukkit.broadcastMessage(lang.get(key, "draw1"));

            Bukkit.getOnlinePlayers().forEach(player -> results(player));
        }
        sender.sendMessage(lang.get(key, "error.pem"));
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
        this.enabled = false;
    }


}
