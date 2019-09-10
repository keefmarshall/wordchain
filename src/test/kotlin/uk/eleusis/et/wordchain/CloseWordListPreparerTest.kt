package uk.eleusis.et.wordchain

import java.io.File
import kotlin.test.*

class CloseWordListPreparerTest {

    @Ignore // takes a while, I'm not going to change this code
    @Test
    fun getCloseWordMap() {
        val map = CloseWordListPreparer.closeWordMap
        assertNotNull(map)
        assertEquals(49028, map.size)
    }

    @Ignore
    @Test
    fun saveCloseWordMap() {
        val filename = "src/main/resources/closeWordMap.obj"
        CloseWordListPreparer.saveCloseWordMap(filename)
        assertTrue(File(filename).exists())
    }

    @Test
    fun loadCloseWordMap() {
        val map = CloseWordList.closeWordList
        assertNotNull(map)
        assertEquals(49028, map.size)
    }
}
