package uk.eleusis.et.wordchain

internal object ChainValidator {

    internal fun pairMatchesChainEdges(chain: Chain): Boolean {
        return chain.pair.first == chain.words.first() && chain.pair.second == chain.words.last()
    }

    internal fun wordsFollowEachOther(wordList: List<String>): Boolean {
        return wordList
            .drop(1)
            .fold(Pair(true, wordList.first())) { (acc, prev): Pair<Boolean, String>, next ->
                Pair( (prev isOneAwayFrom next) && acc, next)
            }
            .first
    }
}

fun Chain.isValid(length: Int? = null): Boolean {
    // Test a few different things:
    // 1 test each word following is one letter away from the previous
    // 2 test the chain starts and ends with words from the pair
    // 3 test the length, if given, is correct
    // TODO test if it's the shortest possible chain (?)

    return ChainValidator.wordsFollowEachOther(this.words) &&
            ChainValidator.pairMatchesChainEdges(this) &&
            this.words.size == length ?: this.words.size
}
