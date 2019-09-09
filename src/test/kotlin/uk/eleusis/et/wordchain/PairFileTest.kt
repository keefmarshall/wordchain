package uk.eleusis.et.wordchain

import org.junit.Assert.*
import org.junit.Test

class PairFileTest {
    @Test
    fun testLoad() {
        val pairs = PairFile.load("src/test/resources/wordpairs-simple.txt")
        assertEquals(3, pairs.size)
        assertEquals(pairs[0].first, "cat")
        assertEquals(pairs[0].second, "dog")
    }
}
