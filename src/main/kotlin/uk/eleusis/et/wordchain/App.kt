package uk.eleusis.et.wordchain

import uk.eleusis.et.wordchain.result.ResultTester

fun main(args: Array<String>) {
    val firstArg = args.getOrElse(0) { "wordpairs.txt" }

    if (firstArg == "-test") {
        val secondArg = args.getOrElse(1) { "wordpairs.out" }
        time { ResultTester.testResultFile(secondArg) }
    } else {
//        CloseWordList.closeWordList // initialise before timing
//        time { App.generateChainsFromFile(firstArg) }
        App.generateChainsFromFile(firstArg)
    }
}

internal object App {
    internal fun generateChainsFromFile(filename: String) {
        val pairs = PairFile.load(filename)
        pairs.parallelStream() //asSequence()
            .map { ChainFinderSwitching.findShortestChain(it) }
            .forEach {
                println("${it.words.size} ${it.words.joinToString(",")}")
            }
    }
}

fun <T> time(block: () -> T): T {
    val startTime = System.currentTimeMillis()
    val retval = block()
    System.err.println("Time taken: ${System.currentTimeMillis() - startTime}ms")
    return retval
}