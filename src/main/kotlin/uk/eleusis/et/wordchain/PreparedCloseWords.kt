package uk.eleusis.et.wordchain

import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object CloseWordListPreparer {
    val closeWordMap = Dictionary.words
        .map { it to Dictionary.wordsCloseTo(it) }
        .toMap(HashMap())

    fun saveCloseWordMap(filename: String) {
        FileOutputStream(filename).use { fs ->
            ObjectOutputStream(fs).use { os ->
                os.writeObject(closeWordMap)
            }
        }
    }
}

object CloseWordList {
    internal val closeWordList = loadCloseWordList();

    @SuppressWarnings("unchecked")
    private fun loadCloseWordList()=
        javaClass.getResource("/closeWordMap.obj").openStream().use { fs ->
            ObjectInputStream(fs).use { os ->
                os.readObject()
            }
        } as Map<String, List<String>>

    fun wordsCloseTo(word: String) = closeWordList.getOrDefault(word, emptyList())
}

