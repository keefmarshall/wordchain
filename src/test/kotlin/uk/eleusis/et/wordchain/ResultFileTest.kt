package uk.eleusis.et.wordchain

import uk.eleusis.et.wordchain.result.ResultFile
import kotlin.test.*

class ResultFileTest {

    @Test
    fun loadAndProcessValidFile() {
        val results = ResultFile.loadAndProcess("src/test/resources/result-test-1.txt")
        assertEquals(3, results.size)
        assertFalse { ResultFile.hasErrors(results) }
        val firstResult = results[0]
        if (firstResult is ResultFile.DataResult) {
            assertEquals(4, firstResult.count)
            assertEquals(4, firstResult.words.size)
            assertEquals("cat", firstResult.words[0])
            assertEquals("dog", firstResult.words[3])
        }
    }

    @Test
    fun loadAndProcessFileWithErrors() {
        val results = ResultFile.loadAndProcess("src/test/resources/result-test-with-errors-1.txt")
        assertEquals(7, results.size)
        assertTrue(ResultFile.hasErrors(results))
        assertTrue { results[0] is ResultFile.DataResult }

        assertTrue { results[1] is ResultFile.ExceptionResult }
        assertTrue { (results[1] as ResultFile.ExceptionResult).exception == null }

        assertTrue { results[2] is ResultFile.ExceptionResult }
        assertTrue { (results[2] as ResultFile.ExceptionResult).exception == null }

        assertTrue { results[3] is ResultFile.ExceptionResult }
        assertTrue { (results[3] as ResultFile.ExceptionResult).exception == null }

        assertTrue { results[4] is ResultFile.ExceptionResult }
        assertTrue { (results[4] as ResultFile.ExceptionResult).exception != null }

        assertTrue { results[5] is ResultFile.DataResult }

        assertTrue { results[6] is ResultFile.ExceptionResult }
        assertTrue { (results[6] as ResultFile.ExceptionResult).exception == null }
    }
}