package uk.eleusis.et.wordchain

import org.junit.Assert.*
import org.junit.Test

class DictionaryTest {
    @Test
    fun testLoadFile() {
        val words = Dictionary.words
        assertEquals(49028, words.size)
    }

    @Test
    fun testWordsCloseTo() {
        val closeWords = Dictionary.wordsCloseTo("cat").toList()
        assertEquals(27, closeWords.size)
        assertTrue(closeWords.contains("bat"))
        assertTrue(closeWords.contains("cot"))
    }
}
