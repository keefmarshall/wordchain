package uk.eleusis.et.wordchain

object Dictionary {
    private const val wordFileName = "/50kwords.txt"

    val words = loadFile()
    val wordsByLength = words.groupBy { it.length }

    private fun loadFile() = javaClass.getResource(wordFileName)
        .readText()
        .split("\n")
        .filter { it.isNotEmpty() }
        .map { it.trim() }

    fun wordsCloseTo(inWord: String) =
        wordsByLength
            .getOrDefault(inWord.length, emptyList())
            .filter { it isOneAwayFrom inWord }
}

