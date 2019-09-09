package uk.eleusis.et.wordchain

import org.junit.Test

import org.junit.Assert.*

class CacheTest {

    @Test
    fun withCache() {
        val cache = Cache<String, String>()
        assertEquals("one", cache.withCache("first") { "one" })
        assertEquals("one", cache.withCache("first") { "two" })
        assertEquals("two", cache.withCache("second") { "two" })
        assertEquals("two", cache.withCache("second") { "one" })
    }
}