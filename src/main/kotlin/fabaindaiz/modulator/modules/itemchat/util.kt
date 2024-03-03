package fabaindaiz.modulator.modules.itemchat

import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.inventory.ItemStack

fun showItem(sender: CommandSender, item: ItemStack) {
    val meta = item.itemMeta !!
    val name = if (meta.hasDisplayName()) meta.displayName else item.type.toString()

    val message = TextComponent("${sender.name} quiere que veas su [$name]")
    message.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_ITEM, item.ge)
    val metaInfo = TextComponent()
    Bukkit.broadcastMessage(message)
}
