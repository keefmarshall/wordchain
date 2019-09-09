package uk.eleusis.et.wordchain

import java.io.File

object PairFile {
    // NB absolutely zero input validation done here!!
    // The challenge spec promises that the file will follow given constraints
    fun load(filename: String) = File(filename).useLines { lines ->
        lines.filter { it.isNotEmpty() }
            .map { it.split(" ") }
            .map { Pair(it[0], it[1]) }
            .toList()
    }
}