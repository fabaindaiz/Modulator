package fabaindaiz.modulator.core.module

import fabaindaiz.modulator.Modulator
import fabaindaiz.modulator.modules.itemchat.Itemchat
import fabaindaiz.modulator.modules.zanakik.Zanakik
import fabaindaiz.modulator.core.util.createFolders

class ModuleLoader (val modulator: Modulator) {
    var modules: List<BaseModule>

    init {
        createFolders(modulator.dataFolder, listOf("config"))
        modules = loadModules()
        loadConfig()

    }

    private fun loadModules(): List<BaseModule> {
        return listOf(Itemchat, Zanakik)
    }

    private fun loadConfig() {
        modules.forEach { it.configuration.injectConfiguration() }

    }

}
