package uk.eleusis.et.wordchain

import kotlin.test.*

class WordUtilsTest {
    @Test
    fun testIsOneAwayFrom() {
        assertTrue { "cat" isOneAwayFrom "cot" }
        assertTrue { "cog" isOneAwayFrom "dog" }
        assertFalse { "cat" isOneAwayFrom "dog" }
        assertFalse { "cat" isOneAwayFrom "gold" }
    }

    @Test
    fun testSameIsNotOneAwayFrom() {
        assertFalse { "cat" isOneAwayFrom "cat" }
    }


    @Test
    fun testIsOneAwayFrom3() {
        assertTrue { "hales" isOneAwayFrom "hares" }
    }

}