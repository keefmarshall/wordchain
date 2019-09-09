package uk.eleusis.et.wordchain

import kotlin.test.*

class ChainValidatorTest {
    @Test
    fun testIsValidForValidChain() {
        assertTrue {
            Chain(Pair("cat", "dog"), listOf("cat", "cot", "cog", "dog")).isValid()
        }
    }

    @Test
    fun testIsValidForInvalidChain() {
        assertFalse {
            Chain(Pair("cat", "dog"), listOf("cat", "bat", "cog", "dog")).isValid()
        }
    }

    @Test
    fun testIsValidForInvalidPair() {
        assertFalse {
            Chain(Pair("cat", "bat"), listOf("cat", "cot", "cog", "dog")).isValid()
        }
    }

    @Test
    fun testIsValidForValidLength() {
        assertFalse {
            Chain(Pair("cat", "bat"), listOf("cat", "cot", "cog", "dog")).isValid(4)
        }
    }

    @Test
    fun testIsValidForInvalidLength() {
        assertFalse {
            Chain(Pair("cat", "bat"), listOf("cat", "cot", "cog", "dog")).isValid(5)
        }
    }

}