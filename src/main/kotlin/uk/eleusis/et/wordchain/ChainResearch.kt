package uk.eleusis.et.wordchain

import kotlin.math.max

/**
 * Helper methods to find valid chains of specific types
 */
object ChainResearch {
    private val finder = ChainFinderSwitching

    fun findAnyChainForWordsOfLength(wordLength: Int, chainLength: Int): Chain? {
        val wordsToTry = Dictionary.words
            .filter { it.length == wordLength }
            .filter { Dictionary.wordsCloseTo(it).isNotEmpty() }
            .shuffled() // otherwise everything always starts with 'a' :)
        return wordsToTry.asSequence()
            .map { word1 ->
                wordsToTry.asSequence()
                    .filter { word2 -> word2 != word1 }
                    .map { word2 ->
                        try {
                            finder.findShortestChain(Pair(word1, word2))
                        } catch (e: NoChainFoundException) {
                            null
                        }
                    }
                    .filterNotNull()
                    .filter { it.words.size == chainLength }
                    .firstOrNull()
            }
            .filterNotNull()
            .firstOrNull()
    }

    fun findLongestChainForRandomWordOfLength(wordLength: Int): Chain {
        val randomWord = Dictionary.words
            .filter { it.length == wordLength }
            .random()

        return ChainFinderSwitching.findLongestChainForWord(randomWord)
    }

    fun findChainLongerThan(minLength: Int = 30, printIf: Int = 25): Chain {
        var maxFound = 0

        var chain: Chain? = null
        while (maxFound < minLength) {
            val randomWord = Dictionary.words
                .filter { it.length in 3..7 }
                .random()

            println("Trying $randomWord, current max is $maxFound")
            chain = ChainFinderSwitching.findLongestChainForWord(randomWord)
            maxFound = max(chain.words.size, maxFound)
            if (chain.words.size > printIf) {
                println(chain)
            }
        }

        if (chain != null) {
            return chain
        }

        throw NoChainFoundException()
    }
}