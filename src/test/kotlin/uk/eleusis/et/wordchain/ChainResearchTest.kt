package uk.eleusis.et.wordchain

import org.junit.Test

import org.junit.Ignore
import kotlin.math.floor

class ChainResearchTest {

    @Ignore // takes a Loooong time!
    @Test
    fun findAnyChainForWordsOfLength() {
        (2..10).forEach {chainlen ->
            (3..7).forEach { wordlen ->
                println("$chainlen, $wordlen: " + ChainResearch.findAnyChainForWordsOfLength(wordlen, chainlen))
            }
        }
    }

    @Test
    fun findRandomChain() {
        val wordLength = (floor(Math.random() * 5) + 3).toInt()
        val chain = ChainResearch.findLongestChainForRandomWordOfLength(wordLength)
        println("${chain.words.size}: $chain")
    }

    @Ignore // takes too long!
    @Test
    fun findRandomChains() {
        (0..10).forEach { _ ->
            val wordLength = (floor(Math.random() * 5) + 3).toInt()
            val chain = ChainResearch.findLongestChainForRandomWordOfLength(wordLength)
            println("${chain.words.size}: $chain")
        }
    }

    @Test
    fun findChainLongerThan() {
        println(ChainResearch.findChainLongerThan(25, 15))
    }

}
