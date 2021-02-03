package fabaindaiz.modulator.module.itemchat;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class itemchatUtil {

    static void showItemSpecs(Modulator modulator, CommandSender sender, ItemStack item, String interactText, boolean norestriction) {
        languageLoader lang = modulator.getConfiguration().getLang();
        ItemMeta itemMeta = item.getItemMeta();

        StringBuilder mainText = new StringBuilder();
        mainText.append("\u00A76");
        mainText.append(sender.getName());
        mainText.append(interactText);

        mainText.append("[");
        if (itemMeta.hasDisplayName()) {
            mainText.append(itemMeta.getDisplayName());
        } else {
            mainText.append(item.getType().toString());
        }
        mainText.append("]");

        int attributes = 0;

        // Name
        BaseComponent text1 = new TextComponent();
        if (itemMeta.hasDisplayName()) {
            attributes++;

            text1.addExtra(lang.get("itemchat.show1"));
            text1.addExtra(itemMeta.getDisplayName());
            text1.addExtra("\n");
        }

        // Material
        BaseComponent text2 = new TextComponent(lang.get("itemchat.show2"));
        text2.addExtra(item.getType().toString());
        text2.addExtra("\n");

        // Lore
        BaseComponent text3 = new TextComponent();
        if (itemMeta.hasLore()) {
            attributes++;

            text3.addExtra(lang.get("itemchat.show3"));
            itemMeta.getLore().forEach(text3::addExtra);
            text3.addExtra("\n");
        }

        // Enchantment
        BaseComponent text4 = new TextComponent();
        if (item.getEnchantments().size() > 0) {
            attributes++;

            text4.addExtra(lang.get("itemchat.show4"));
            item.getEnchantments().forEach((enchantment, integer) -> {
                text4.addExtra(enchantment.getKey().getKey());
                text4.addExtra(" ");
                text4.addExtra(String.valueOf(integer));
                text4.addExtra(", ");
            });
            text4.addExtra("\n");
        }

        BaseComponent text5 = new TextComponent();
        if (itemMeta.hasCustomModelData()) {
        }

        BaseComponent text6 = new TextComponent();
        if (attributes <= 0) {
            if (!norestriction) {
                sender.sendMessage(lang.get("itemchat.error2"));
                return;
            }
            text6.addExtra(lang.get("itemchat.show5"));
        }

        BaseComponent[] itemText = {text1, text2, text3, text4, text6};

        BaseComponent itemShow = new TextComponent(mainText.toString());
        itemShow.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, itemText));

        Bukkit.getOnlinePlayers().forEach(player -> player.spigot().sendMessage(itemShow));
    }
}
