import com.google.common.collect.Lists;
import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dispatcher.CommandDispatcher;
import fabaindaiz.modulator.core.modules.IModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static fabaindaiz.modulator.core.util.playersUtil.isOnlinePlayer;

public class orderCommand extends CommandDispatcher {
    private final String key = "order.command";

    protected orderCommand(Modulator modulator, IModule module) {
        super(modulator, module);

        register("", this::info);
        register("help", this::help);
        register("inv", this::order);
        register("stack", this::stack);
        register("sort", this::sort);
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

    private boolean order(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        if (!isOnlinePlayer(sender.getName())) {
            sender.sendMessage(lang.get(key, "error2"));
            return true;
        }
        Player player = Bukkit.getPlayer(sender.getName());
        ItemStack[] inventory = player.getInventory().getStorageContents();

        inventory = stackMaterial(inventory);
        inventory = sortMaterial(inventory);

        player.getInventory().setStorageContents(inventory);
        player.updateInventory();
        return true;
    }

    private boolean stack(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        if (!isOnlinePlayer(sender.getName())) {
            sender.sendMessage(lang.get(key, "error2"));
            return true;
        }
        Player player = Bukkit.getPlayer(sender.getName());
        ItemStack[] inventory = player.getInventory().getStorageContents();

        inventory = stackMaterial(inventory);

        player.getInventory().setStorageContents(inventory);
        player.updateInventory();
        return true;
    }

    private boolean sort(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        if (!isOnlinePlayer(sender.getName())) {
            sender.sendMessage(lang.get(key, "error2"));
            return true;
        }
        Player player = Bukkit.getPlayer(sender.getName());
        ItemStack[] inventory = player.getInventory().getStorageContents();

        inventory = sortMaterial(inventory);

        player.getInventory().setStorageContents(inventory);
        player.updateInventory();
        return true;
    }

    private ItemStack[] sortMaterial(ItemStack[] inventory) {
        List<ItemStack> list = Lists.newArrayList(inventory);
        Collections.sort(list, (t1, t2) -> {
            Material obj1 = (t1 == null) ? Material.AIR : t1.getType();
            Material obj2 = (t2 == null) ? Material.AIR : t2.getType();
            return obj1.compareTo(obj2);
        });
        return list.toArray(new ItemStack[list.size()]);
    }

    private ItemStack[] stackMaterial(ItemStack[] inventory) {
        List<ItemStack> list = Lists.newArrayList(inventory);
        HashMap<ItemStack, Integer> materials = new HashMap<>();

        List<ItemStack> stacked = new ArrayList<>();

        list.forEach((item -> {
            if (item == null) {

            } else if (item.getType().getMaxStackSize() > 1) {
                ItemStack cloneItem = item.clone();
                cloneItem.setAmount(1);
                if (materials.containsKey(cloneItem)) {
                    materials.replace(cloneItem, materials.get(cloneItem) + item.getAmount());
                } else {
                    materials.put(cloneItem, item.getAmount());
                }
            } else {
                stacked.add(item);
            }
        }));
        materials.forEach((item, amount)->{
            int maxStack = item.getType().getMaxStackSize();
            item.setAmount(maxStack);
            for (int i = 0; i < amount/maxStack; i++) {
                ItemStack cloneItem = item.clone();
                stacked.add(cloneItem);
            }
            item.setAmount(amount%maxStack);
            stacked.add(item);
        });
        return stacked.toArray(new ItemStack[stacked.size()]);
    }

}