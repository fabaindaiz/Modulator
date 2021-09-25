import com.google.common.collect.Lists;
import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dispatcher.CommandDispatcher;
import fabaindaiz.modulator.core.dispatcher.SubCommandDispatcher;
import fabaindaiz.modulator.core.modules.IModule;
import io.papermc.paper.inventory.ItemRarity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Function;

import static fabaindaiz.modulator.core.util.playersUtil.isOnlinePlayer;

public class orderCommand extends CommandDispatcher {
    private final String key = "order.command";

    private final HashMap<String, Function<ItemStack ,Comparable>> compareMap = new HashMap<>();
    private final SubCommandDispatcher orderDispatcher = new SubCommandDispatcher(this);
    private final SubCommandDispatcher sortDispatcher = new SubCommandDispatcher(this);

    protected orderCommand(Modulator modulator, IModule module) {
        super(modulator, module);

        register("", this::info);
        register("help", this::help);
        register("inv", this::order);
        register("sort", this::sort);
        register("stack", this::stack);

        orderDispatcher.register(1, this::order1);
        orderDispatcher.register(2, this::order2);

        sortDispatcher.register(1, this::sort1);
        sortDispatcher.register(2, this::sort2);

        compareMap.put("amount", this::compareAmount);
        compareMap.put("durability", this::compareDurability);
        compareMap.put("material", this::compareMaterial);
        compareMap.put("name", this::compareName);
        compareMap.put("rarity", this::compareRarity);
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
        if (!isOnlinePlayer(sender.getName())) {
            sender.sendMessage(lang.get(key, "error2"));
            return true;
        }
        return orderDispatcher.dispatch(sender, args).apply(sender, args);
    }

    private boolean order1(CommandSender sender, ArrayList<String> args) {
        Player player = Bukkit.getPlayer(sender.getName());
        ItemStack[] inventory = player.getInventory().getStorageContents();

        inventory = stackMaterial(inventory);
        inventory = sortAbstract(inventory, this::compareMaterial);

        player.getInventory().setStorageContents(inventory);
        player.updateInventory();
        return true;
    }

    private boolean order2(CommandSender sender, ArrayList<String> args) {
        Player player = Bukkit.getPlayer(sender.getName());
        ItemStack[] inventory = player.getInventory().getStorageContents();

        inventory = stackMaterial(inventory);
        inventory = sortAbstract(inventory, compareMap.getOrDefault(args.get(1), this::compareNone));

        player.getInventory().setStorageContents(inventory);
        player.updateInventory();
        return true;
    }

    private boolean sort(CommandSender sender, ArrayList<String> args) {
        if (!isOnlinePlayer(sender.getName())) {
            sender.sendMessage(lang.get(key, "error2"));
            return true;
        }
        return sortDispatcher.dispatch(sender, args).apply(sender, args);
    }

    private boolean sort1(CommandSender sender, ArrayList<String> args) {
        Player player = Bukkit.getPlayer(sender.getName());
        ItemStack[] inventory = player.getInventory().getStorageContents();

        inventory = sortAbstract(inventory, this::compareMaterial);

        player.getInventory().setStorageContents(inventory);
        player.updateInventory();
        return true;
    }

    private boolean sort2(CommandSender sender, ArrayList<String> args) {
        Player player = Bukkit.getPlayer(sender.getName());
        ItemStack[] inventory = player.getInventory().getStorageContents();

        inventory = sortAbstract(inventory, compareMap.getOrDefault(args.get(1), this::compareNone));

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

    // Auxiliary methods

    private ItemStack[] sortAbstract(ItemStack[] inventory, Function<ItemStack ,Comparable> method) {
        List<ItemStack> list = Lists.newArrayList(inventory);
        Collections.sort(list, Comparator.comparing(method::apply));
        return list.toArray(new ItemStack[list.size()]);
    }

    private Comparable compareAmount(ItemStack stack) {
        return (stack == null) ? 0 : stack.getAmount();
    }

    private Comparable compareDurability(ItemStack stack) {
        return (stack == null) ? 0 : stack.getDurability();
    }

    private Comparable compareMaterial(ItemStack stack) {
        return (stack == null) ? Material.AIR : stack.getType();
    }

    private Comparable compareName(ItemStack stack) {
        return (stack == null) ? "" : stack.getI18NDisplayName();
    }

    private Comparable compareRarity(ItemStack stack) {
        return (stack == null) ? ItemRarity.COMMON : stack.getRarity();
    }

    private Comparable compareNone(ItemStack stack) {
        return  "";
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