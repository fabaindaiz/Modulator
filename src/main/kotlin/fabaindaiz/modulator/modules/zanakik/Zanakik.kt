package fabaindaiz.modulator.modules.zanakik

import fabaindaiz.modulator.core.module.BaseModule
import fabaindaiz.modulator.core.module.Configuration

object Zanakik : BaseModule {
    override val name = "Zanakik"
    override val description = "Kick players when server is full"
    override val aliases = listOf("zkik")

    override val executor = ZanakikExecutor
    override val tabCompleter = ZanakikTabCompleter
    override val configuration = Configuration()

    override fun onEnable() {
    }

    override fun onDisable() {
    }

}