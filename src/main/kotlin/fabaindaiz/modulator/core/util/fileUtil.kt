package fabaindaiz.modulator.core.util

import java.io.File

fun createFolders(base: File, folders: List<String>) {
    folders.forEach { createFolder(base, it) }
}

fun createFolder (base: File, folder: String) {
    val file = File(base, folder)
    if (!file.exists()) {
        file.mkdir()
    }
}
