package com.neoterux.espfier.utils

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

fun openDir(path: String, vararg more:String? = emptyArray(), pathConfigAction: ((Path) -> Unit)? = null) =
    Paths.get(path, *more)
            .apply { pathConfigAction?.invoke(this) }
            .toFile()

fun File.filteredFiles(filter: (File) -> Boolean): List<File> = this.listFiles()
        .filter(filter)


fun File.filterFiles(name: String = "*", ext: String = "*") =
    filteredFiles { it.isFile && it.name.run {
        endsWith(ext, true) && startsWith(name, true)
    } }

fun listFiles(path: String, vararg more: String = emptyArray()) = Files.walk(Paths.get(path, *more))
