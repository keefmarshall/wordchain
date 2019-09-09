package uk.eleusis.et.wordchain

object Dictionary {
    private const val wordFileName = "/50kwords.txt"

    val words = loadFile()

    private fun loadFile() = javaClass.getResource(wordFileName)
        .readText()
        .split("\n")
        .filter { it.isNotEmpty() }
        .map { it.trim() }

    fun wordsCloseTo(inWord: String) =
        words
            .filter { it.length == inWord.length }
            .filter { it isOneAwayFrom inWord }
}

