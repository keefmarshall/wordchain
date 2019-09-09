package uk.eleusis.et.wordchain.result

import uk.eleusis.et.wordchain.Chain
import uk.eleusis.et.wordchain.isValid

object ResultTester {

    fun testResultFile(filename: String) {
        val results = ResultFile.loadAndProcess(filename)
        if (ResultFile.hasErrors(results)) {
            println("Result file has syntax errors:")
            results.filterIsInstance<ResultFile.ExceptionResult>().forEach(::println)
            return
        } else {
            println("Result file syntax is correct")
        }

        val allChains = results
            .filterIsInstance<ResultFile.DataResult>()
            .map { Chain(Pair(it.words.first(), it.words.last()), it.words) }

        val invalidChains = allChains.filterNot { it.isValid() }

        if (invalidChains.isEmpty()) {
            println("All ${allChains.size} chains valid.")
        } else {
            println("Found ${invalidChains.size} invalid chains out of ${allChains.size}:")
            invalidChains.forEach(::println)
        }
    }

}