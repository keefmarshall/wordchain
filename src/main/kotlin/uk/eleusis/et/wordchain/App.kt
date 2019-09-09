package uk.eleusis.et.wordchain

import uk.eleusis.et.wordchain.result.ResultTester

fun main(args: Array<String>) {
    val firstArg = args.getOrElse(0) { _ -> "wordpairs.txt" }

    if (firstArg == "-test") {
        val secondArg = args.getOrElse(1) { _ -> "wordpairs.out" }
        time { ResultTester.testResultFile(secondArg) }
    } else {
        time { App.generateChainsFromFile(firstArg) }
    }
}

internal object App {
    internal fun generateChainsFromFile(filename: String) {
        val pairs = PairFile.load(filename)
        pairs.asSequence()
            .filter { Dictionary.words.contains(it.first) && Dictionary.words.contains(it.second) }
            .map { ChainFinderSwitching.findShortestChain(it) }
            .forEach {
                if (!it.isValid()) {
                    System.err.println("ERROR: invalid chain below:")
                }
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