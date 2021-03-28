import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import fabaindaiz.modulator.core.loader.moduleLang;
import fabaindaiz.modulator.modules.IModule;
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
    private final moduleLang lang;
    private final boolean usevault;
    private final int price;
    private final int collect;
    private boolean enabled;
    private final lotteryStorage storage;

    protected lotteryCommand(Modulator modulator, lotteryStorage storage, IModule module) {

        this.plugin = modulator;
        this.module = module;
        this.lang = module.getLang();
        this.enabled = module.getConfig().getBoolean("lottery.enable");
        this.usevault = module.getConfig().getBoolean("lottery.usevault");
        this.price = module.getConfig().getInt("lottery.pricelottery");
        this.collect = module.getConfig().getInt("lottery.collect");
        this.storage = storage;

        storage.setPrice(this.price);
        if (!plugin.getVault().getServerHasVault()) {
            Bukkit.getLogger().warning(lang.get("lottery.novault1"));
            if (this.usevault) {
                this.enabled = false;
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!this.enabled) {
            sender.sendMessage(lang.get("lottery.disable1"));
            return true;
        }

        switch (args.length) {
            case 0:
                sender.sendMessage(lang.get("lottery.info1"));
                sender.sendMessage(lang.get("lottery.collect4") + collect);
                sender.sendMessage(lang.get("lottery.info2"));
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
                        sender.sendMessage(lang.get("lottery.error1"));
                        return true;
                }
            default:
                sender.sendMessage(lang.get("lottery.error1"));
                return true;
        }
    }

    private void help(CommandSender sender) {
        sender.sendMessage(lang.get("lottery.help1"));
        sender.sendMessage(lang.get("lottery.help2"));
        sender.sendMessage(lang.get("lottery.help3"));
        sender.sendMessage(lang.get("lottery.help4"));
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
            EconomyResponse response = plugin.getVault().getEconomy().depositPlayer(Bukkit.getPlayer(sender.getName()), this.collect);

            if (!response.transactionSuccess()) {
                sender.sendMessage(lang.get("lottery.error5"));

                ItemMeta meta = item.getItemMeta();
                meta.getLore().set(1, meta.getLore().get(1) + lang.get("lottery.collect3"));

                Bukkit.getPlayer(sender.getName()).getInventory().getItemInMainHand().setItemMeta(meta);
                return;
            }
            Bukkit.getPlayer(sender.getName()).getInventory().remove(item);
            sender.sendMessage(lang.get("lottery.collect1"));

            Bukkit.broadcastMessage(sender.getName() + lang.get("lottery.winner1"));
        } else {
            sender.sendMessage(lang.get("lottery.collect2"));
        }
    }

    private void draw(CommandSender sender) {
        if (sender.hasPermission("modulator.lotteryDraw")) {
            this.storage.drawWinner();
            Bukkit.broadcastMessage(lang.get("lottery.draw1"));

            Bukkit.getOnlinePlayers().forEach(player -> results(player));
        }
        sender.sendMessage(lang.get("modulator.error2"));
    }

    private void results(CommandSender sender) {
        StringBuilder string = new StringBuilder();
        string.append(lang.get("lottery.info4"));

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
