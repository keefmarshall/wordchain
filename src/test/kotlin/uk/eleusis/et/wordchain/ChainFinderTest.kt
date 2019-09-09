package uk.eleusis.et.wordchain

import org.junit.Test

import org.junit.Assert.*

class ChainFinderTest {

    @Test
    fun findChainForDirectPair() {
        val chain = ChainFinder.findChain(Pair("cat", "cot"))
        println(chain)
        assertEquals(2, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findChainFor1Apart() {
        val chain = ChainFinder.findChain(Pair("cat", "cog"))
        println(chain)
        assertEquals(3, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findChainFor2Apart() {
        val chain = ChainFinder.findChain(Pair("cat", "dog"))
        println(chain)
        assertEquals(4, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findChainFor2ApartWithFourLetters() {
        val chain = ChainFinder.findChain(Pair("lead", "gold"))
        println(chain)
        assertEquals(4, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findLongerChain1() {
        val chain = ChainFinder.findChain(Pair("ruby", "code"))
        println(chain)
        assertEquals(5, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findLongerChain3() {
        val chain = ChainFinder.findChain(Pair("buck", "exon"))
        println(chain)
        assertEquals(12, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findLongerChain4() {
        val chain = ChainFinder.findChain(Pair("hares", "niche"))
        println(chain)
        assertEquals(17, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test(expected = NoChainFoundException::class)
    fun findNonExistentChain() {
        val chain = ChainFinder.findChain(Pair("driving", "swimming"))
    }

}