import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.LanguageLoader;
import fabaindaiz.modulator.core.modules.IModule;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;

public class lotteryStorage implements Listener {
    final HashMap<String, Inventory> lotteries = new HashMap<>();
    final HashMap<String, int[]> numbers = new HashMap<>();
    private final Modulator plugin;
    private final IModule module;
    private final LanguageLoader lang;
    private final String key = "lottery.storage";
    private final boolean usevault;
    private final boolean novalid;
    private int price = 1000;

    ArrayList<Integer> winner = lotteryUtil.setWinner();
    String winnerCode = lotteryUtil.arrayToCode(winner);

    protected lotteryStorage(Modulator plugin, IModule module) {
        this.plugin = plugin;
        this.module = module;
        this.lang = module.getLanguageLoader();
        this.usevault = module.getConfiguration().getBoolean("lottery.usevault");
        this.novalid = module.getConfiguration().getBoolean("lottery.novalid");
    }

    protected void drawWinner() {
        this.winner = lotteryUtil.setWinner();
        this.winnerCode = lotteryUtil.arrayToCode(this.winner);
    }

    protected void newLottery(CommandSender sender) {
        String senderName = sender.getName();

        if (lotteries.containsKey(senderName)) {
            return;
        }

        Inventory inventory = Bukkit.createInventory(null, 18, lang.get(key, "info3"));

        inventory.setItem(8, lotteryUtil.getWhiteGlass(lang, 0));
        inventory.setItem(17, lotteryUtil.getRedWool(lang.get(key, "give3")));

        for (int i = 0; i < 7; i++) {
            inventory.setItem(i, lotteryUtil.getRedWool(String.valueOf(i + 1)));
        }
        for (int i = 7; i < 14; i++) {
            inventory.setItem(i + 2, lotteryUtil.getRedWool(String.valueOf(i + 1)));
        }

        lotteries.put(senderName, inventory);
        numbers.put(senderName, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    }

    protected void click(String senderName, int slot) {
        Inventory playerInv = lotteries.get(senderName);
        int[] playerNumbers = numbers.get(senderName);
        int selected = Integer.parseInt(playerInv.getItem(8).getItemMeta().getDisplayName());

        if (selected <= 8) {
            if (slot < 7 || (slot >= 9 && slot <= 15)) {
                int number = lotteryUtil.slotToNumber(slot);

                // if was red
                if (playerNumbers[number] == 0) {
                    if (selected == 8) {
                        return;
                    }
                    playerInv.setItem(slot, lotteryUtil.getGreenWool(String.valueOf(number + 1)));
                    playerInv.setItem(8, lotteryUtil.getWhiteGlass(lang, selected + 1));
                    numbers.get(senderName)[number] = 1;

                    if (selected == 7) {
                        playerInv.setItem(17, lotteryUtil.getGreenWool(lang.get(key, "give3")));
                    }
                    return;
                } else {
                    playerInv.setItem(slot, lotteryUtil.getRedWool(String.valueOf(number + 1)));
                    playerInv.setItem(8, lotteryUtil.getWhiteGlass(lang, selected - 1));
                    numbers.get(senderName)[number] = 0;

                    if (selected == 8) {
                        playerInv.setItem(17, lotteryUtil.getRedWool(lang.get(key, "give3")));
                    }
                    return;
                }
            }
        }
        if (selected == 8 && slot == 17) {
            giveTicket(Bukkit.getPlayer(senderName));
        }
    }

    protected void openInventory(Player player) {
        player.openInventory(lotteries.get(player.getName()));
    }

    protected void giveTicket(Player player) {
        String senderName = player.getName();

        ArrayList<String> lore = getLore(senderName);
        if (lore.get(1).equals(winnerCode)) {
            player.sendMessage(lang.get(key, "error2"));
            return;
        }

        if (this.novalid) {
            lore.set(1, lore.get(1) + lang.get(key, "error3"));
        }
        if (this.usevault) {
            EconomyResponse response = plugin.getDependencies().getVaultHandler().getEconomy().withdrawPlayer(player, price);
            if (!response.transactionSuccess()) {
                player.sendMessage(lang.get(key, "error4"));
                return;
            }
        }

        player.sendMessage(lang.get(key, "give2"));
        player.getInventory().addItem(lotteryUtil.getLotteryTicker(lang, senderName, lore));
    }

    protected boolean containsPlayer(String senderName) {
        return lotteries.containsKey(senderName);
    }

    protected Inventory getInventory(String senderName) {
        return lotteries.get(senderName);
    }

    protected ArrayList<String> getLore(String senderName) {
        int[] playerNumbers = numbers.get(senderName);
        ArrayList<String> loresList = new ArrayList<>();

        StringBuilder lotteryLore = new StringBuilder();
        StringBuilder lotteryCode = new StringBuilder();
        lotteryLore.append(lang.get(key, "give1"));

        for (int i = 0; i < 14; i++) {
            if (playerNumbers[i] == 1) {
                lotteryLore.append(i + 1);
                lotteryLore.append(" ");
                lotteryCode.append("1");
            } else {
                lotteryCode.append("0");
            }
        }

        loresList.add(lotteryLore.toString());
        loresList.add(lotteryCode.toString());
        return loresList;
    }

    protected Boolean isWinner(String code) {
        return winnerCode.equals(code);
    }


    public void setPrice(int price) {
        this.price = price;
    }
}
