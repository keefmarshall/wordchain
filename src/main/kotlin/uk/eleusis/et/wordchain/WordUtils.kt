package uk.eleusis.et.wordchain

infix fun String.isOneAwayFrom(word2: String): Boolean {
    val oneChars = this.toCharArray()
    val twoChars = word2.toCharArray()
    var diffs = 0;
    for (i in 0 until oneChars.size) {
        if (oneChars[i] != twoChars[i]) {
            diffs ++;
            if (diffs > 1) {
                break
            }
        }
    }

    return diffs == 1
}
