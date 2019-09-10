package uk.eleusis.et.wordchain

import org.junit.Ignore
import org.junit.Test

import org.junit.Assert.*

class ChainFinderSwitchingTest {
    private val finder = ChainFinderSwitching

    @Test
    fun findChainForDirectPair() {
        val chain = finder.findShortestChain(Pair("cat", "cot"))
        println(chain)
        assertEquals(2, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findChainFor1Apart() {
        val chain = finder.findShortestChain(Pair("cat", "cog"))
        println(chain)
        assertEquals(3, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findChainFor1Apart2() {
        val chain = finder.findShortestChain(Pair("cat", "bit"))
        println(chain)
        assertEquals(3, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findChainFor2Apart() {
        val chain = finder.findShortestChain(Pair("cat", "dog"))
        println(chain)
        assertEquals(4, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findChainFor2Apart2() {
        val chain = finder.findShortestChain(Pair("cat", "fog"))
        println(chain)
        assertEquals(4, chain.words.size)
        assertTrue(chain.isValid())
    }


    @Test
    fun findChainFor2ApartWithFourLetters() {
        val chain = finder.findShortestChain(Pair("lead", "gold"))
        println(chain)
        assertEquals(4, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findLongerChain1() {
        val chain = finder.findShortestChain(Pair("ruby", "code"))
        println(chain)
        assertEquals(5, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findLongerChain2() {
        val chain = finder.findShortestChain(Pair("plead", "creep"))
        println(chain)
        assertEquals(7, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findLongerChain3() {
        val chain = finder.findShortestChain(Pair("buck", "exon"))
        println(chain)
        assertEquals(12, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findLongerChain4() {
        val chain = finder.findShortestChain(Pair("hares", "niche"))
        println(chain)
        assertEquals(17, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test(expected = NoChainFoundException::class)
    fun findNonExistentChain() {
        val chain = finder.findShortestChain(Pair("driving", "swimming"))
    }





    @Test
    fun nextLayer() {
        val next = finder.nextLayer(setOf("cat", "dog"), setOf("cat", "dog", "cot"))
        assertTrue(next.isNotEmpty())
        assertFalse(next.contains("cot"))
        assertFalse(next.contains("cat"))
        assertFalse(next.contains("dog"))
        assertTrue(next.contains("bat"))
        assertEquals(48, next.size)

        val next2 = finder.nextLayer(next, setOf("cat", "dog", "cot") + next)
        assertTrue(next2.isNotEmpty())
        assertFalse(next2.contains("cot"))
        assertFalse(next2.contains("cat"))
        assertFalse(next2.contains("dog"))
        assertFalse(next2.contains("bat"))
        assertTrue(next2.contains("col"))
        assertEquals(375, next2.size)

    }

    @Test
    fun findLongestChainForWordWithNone() {
        val chain = finder.findLongestChainForWord("engine")
        assertEquals(1, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findLongestChainForWord() {
        val chain = finder.findLongestChainForWord("smart")
        println(chain)
        assertEquals(24, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Ignore // 'hales' is not in the dictionary so this breaks for precached lists
    @Test
    fun findLongestChainForWord2() {
        val chain = finder.findLongestChainForWord("hales")
        println(chain)
        assertEquals(18, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findLongestChainForWord3() {
        val chain = finder.findLongestChainForWord("buck")
        println(chain)
        assertEquals(12, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findLongestChainForWord4() {
        val chain = finder.findLongestChainForWord("niche")
        println(chain)
        assertEquals(31, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findLongestChainForWord5() {
        val chain = finder.findLongestChainForWord("sneaky")
        println(chain)
        assertEquals(35, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findLongestChainForWord6() {
        val chain = finder.findLongestChainForWord("bloomer")
        println(chain)
        assertEquals(32, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findLongestChainForWord7() {
        val chain = finder.findLongestChainForWord("dredge")
        println(chain)
        assertEquals(35, chain.words.size)
        assertTrue(chain.isValid())
    }

    @Test
    fun findLongestChainForWord8() {
        val chain = finder.findLongestChainForWord("miffed")
        println(chain)
        assertEquals(35, chain.words.size)
        assertTrue(chain.isValid())
    }
}