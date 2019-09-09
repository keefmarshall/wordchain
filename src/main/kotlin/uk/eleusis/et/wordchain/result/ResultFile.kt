package uk.eleusis.et.wordchain.result

import java.io.File

object ResultFile {

    interface Result
    data class DataResult(val count: Int, val words: List<String>) : Result
    data class ExceptionResult(val exceptionMessage: String, val exception: Exception? = null) :
        Result

    fun loadAndProcess(filename: String): List<Result> = File(filename).useLines { lines ->
        // Each line should be "%d %s,%s,%s.." - count followed by word list
        // empty / blank lines are allowed
        lines
            .filter { it.isNotBlank() } // lines with just whitespace are allowed, but ignored
            .map { buildResult(it) }
            .toList()
    }

    private fun buildResult(line: String): Result {
        val bits = line.split(" ")

        if (bits.size != 2) {
            return ExceptionResult("Incorrect number of entries on line: ${bits.size} should be 2")
        }

        if (bits[1].isBlank() || !bits[1].contains(",")) {
            return ExceptionResult("Word chain part of line is not correctly formatted: '${bits[1]}'")
        }

        return try {
            val count = bits[0].toInt()
            val words = bits[1].split(",")
            if (words.size != count) {
                ExceptionResult("Word list size not equal to count: $line")
            } else {
                DataResult(count, words)
            }
        } catch (e: Exception) {
            ExceptionResult(e.message ?: e.javaClass.name, e)
        }
    }

    fun hasErrors(results: List<Result>): Boolean = results.any { it is ExceptionResult }
}