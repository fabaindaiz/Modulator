import fabaindaiz.modulator.core.configuration.LanguageLoader;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class lotteryUtil {

    private static String key = "lottery.util";

    static protected ItemStack getLotteryTicker(LanguageLoader lang, String senderName, ArrayList<String> loresList) {
        ItemStack lottery = new ItemStack(Material.PAPER);
        ItemMeta meta = lottery.getItemMeta();
        meta.setDisplayName(lang.get(key, "lore2"));

        meta.setLore(loresList);
        lottery.setItemMeta(meta);
        return lottery;
    }

    static protected ItemStack getRedWool(String text) {

        ItemStack wool = new ItemStack(Material.RED_WOOL);
        ItemMeta meta = wool.getItemMeta();
        meta.setDisplayName(text);

        wool.setItemMeta(meta);
        return wool;
    }

    static protected ItemStack getGreenWool(String text) {

        ItemStack wool = new ItemStack(Material.GREEN_WOOL);
        ItemMeta meta = wool.getItemMeta();
        meta.setDisplayName(text);

        wool.setItemMeta(meta);
        return wool;
    }

    static protected ItemStack getWhiteGlass(LanguageLoader lang, int num) {

        ItemStack glass = new ItemStack(Material.WHITE_STAINED_GLASS);
        ItemMeta meta = glass.getItemMeta();
        meta.setDisplayName(String.valueOf(num));

        List<String> loresList = new ArrayList<>();
        loresList.add(lang.get(key, "lore1"));

        meta.setLore(loresList);

        glass.setItemMeta(meta);
        return glass;
    }

    static protected ArrayList<Integer> setWinner() {
        ArrayList<Integer> winner = new ArrayList<>();
        final Random rnd = new Random();

        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14));
        for (int i = 0; i < 8; i++) {

            int num = rnd.nextInt(numbers.size());
            winner.add(numbers.get(num));
            numbers.remove(num);
        }
        winner.sort(Comparator.naturalOrder());

        return winner;
    }

    static protected String arrayToCode(ArrayList<Integer> winners) {
        StringBuilder lotteryLore = new StringBuilder();
        for (int i = 1; i <= 14; i++) {
            if (winners.contains(i)) {
                lotteryLore.append("1");
            } else {
                lotteryLore.append("0");
            }
        }
        return lotteryLore.toString();
    }

    static protected int slotToNumber(int slot) {
        if (slot >= 7) {
            slot -= 2;
        }
        return slot;
    }

}
