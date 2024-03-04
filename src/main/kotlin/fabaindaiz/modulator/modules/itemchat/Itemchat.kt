package fabaindaiz.modulator.modules.itemchat

import fabaindaiz.modulator.core.module.BaseModule
import fabaindaiz.modulator.core.module.Configuration

object Itemchat : BaseModule {
    override val name = "Itemchat"
    override val description = "Muestra tus items por el chat"
    override val aliases = listOf("itemchat")

    override val executor = ItemchatExecutor
    override val tabCompleter = ItemchatTabCompleter
    override val configuration = Configuration()

    override fun onEnable() {
    }

    override fun onDisable() {
    }

}