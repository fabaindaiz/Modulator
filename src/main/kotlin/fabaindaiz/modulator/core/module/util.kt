package fabaindaiz.modulator.core.module

import java.io.File

fun createFolders(base: File, folders: List<String>) {
    for (folder in folders) {
        createFolder(base, folder)
    }
}
fun createFolder (base: File, folder: String) {
    val file = File(base, folder)
    if (!file.exists()) {
        file.mkdir()
    }
}
