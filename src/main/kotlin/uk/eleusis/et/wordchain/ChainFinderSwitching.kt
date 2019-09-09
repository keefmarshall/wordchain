package uk.eleusis.et.wordchain

object ChainFinderSwitching {

    fun findShortestChain(pair: Pair<String, String>): Chain {

        val layerSets = findLayers(setOf(pair.first), setOf(pair.second), setOf(pair.first), setOf(pair.second))
        if (layerSets != null) {
            // at least we know there is a chain
//            println("Found a chain of size ${layerSetsForward.size}")
//            layerSets.forEach { println(it.joinToString(",")) }

            // now we have to find an actual chain of words from it
            val chainStrings = findChainFromLayers(layerSets)
            return Chain(pair, chainStrings)
        }

        throw NoChainFoundException()
    }

    /**
     * The algorithm below finds only a set of layers of words that could form possible chains.
     * It doesn't actually find valid individual chains. This method takes the first word from the
     * first layer, and then finds the first word which is one away in the next layer, and so on, to
     * produce an actual word chain
     */
    private fun findChainFromLayers(layers: List<Set<String>>): List<String> {
        val first = layers.first().first()
        return layers.drop(1).fold(listOf(first)) { acc, layer ->
            acc + layer.asSequence().filter { it isOneAwayFrom acc.last() }.first()
        }
    }

    /**
     * This checks for intersections between two sets.
     * If there is no intersection, it finds all possible words that could be next in the chain
     * and recurses to another level.
     * It returns a Set of all the layers it has found, unless it can't find anything in which
     * case it returns null.
     *
     * (It switches direction each time through - this reduces the total search time significantly.)
     */
    private fun findLayers(
        set1: Set<String>,
        set2: Set<String>,
        seenForward: Set<String>,
        seenBackward: Set<String>,
        forward: Boolean = true
    ): List<Set<String>>? {

        val intersect = set1 intersect set2
        if (intersect.isNotEmpty()) {
            return listOf(intersect)
        }

        // Find all possible next words
        val nextLevel = if (forward) nextLayer(set1, seenForward) else nextLayer(set2, seenBackward)

        if (nextLevel.isNotEmpty()) {
            val subChain = if (forward) {
                findLayers(nextLevel, set2, seenForward.plus(nextLevel), seenBackward, false)
            } else {
                findLayers(set1, nextLevel, seenForward, seenBackward.plus(nextLevel), true)
            }

            if (subChain != null) {
                return if (forward) {
                    val potentials = subChain.first().flatMap { Dictionary.wordsCloseTo(it) }
                    val validWords = set1 intersect potentials
                    listOf(validWords) + subChain
                } else {
                    val potentials = subChain.last().flatMap { Dictionary.wordsCloseTo(it) }
                    val validWords = set2 intersect potentials
                    subChain + listOf(validWords)
                }
            }
        }

        return null;
    }

//    private fun findLayersForwards(
//        set1: Set<String>,
//        set2: Set<String>,
//        seenForward: Set<String>,
//        seenBackward: Set<String>
//    ): List<Set<String>>? {
//
//        val intersect = set1 intersect set2
//        if (intersect.isNotEmpty()) {
//            return listOf(intersect)
//        }
//
//        // Find all possible next words
//        val nextLevel = nextLayer(set1, seenForward)
//
//        if (nextLevel.isNotEmpty()) {
//            val subChain = findLayersBackwards(nextLevel, set2, seenForward.plus(nextLevel), seenBackward)
//
//            if (subChain != null) {
//                val potentials = subChain.first().flatMap { Dictionary.wordsCloseTo(it) }
//                val validWords = set1 intersect potentials
//                return listOf(validWords) + subChain
//            }
//        }
//
//        return null;
//    }
//
//    private fun findLayersBackwards(
//        set1: Set<String>,
//        set2: Set<String>,
//        seenForward: Set<String>,
//        seenBackward: Set<String>
//    ): List<Set<String>>? {
//
//        val intersect = set1 intersect set2
//        if (intersect.isNotEmpty()) {
//            return listOf(intersect)
//        }
//
//        // Find all possible next words
//        val nextLevel = nextLayer(set2, seenBackward)
//
//        if (nextLevel.isNotEmpty()) {
//            val subChain = findLayersForwards(set1, nextLevel, seenForward, seenBackward.plus(nextLevel))
//
//            if (subChain != null) {
//                val potentials = subChain.last().flatMap { Dictionary.wordsCloseTo(it) }
//                val validWords = set2 intersect potentials
//                return subChain + listOf(validWords)
//            }
//        }
//
//        return null;
//    }


    fun findLongestChainForWord(word: String): Chain {
        var layers: List<Set<String>> = listOf(setOf(word))
        var seen = emptySet<String>()
        var done = false
        while (!done) {
            seen = seen + layers.last()
            val next = nextLayer(layers.last(), seen)
            if (next.isNotEmpty()) {
                layers = layers.plus(listOf(next)) // for some reason Kotlin can't correctly infer when I pass just 'next'
            } else {
                done = true
                println()
            }
        }

        val wordChain = findChainFromLayers(layers.reversed()).reversed()
        return Chain(Pair(wordChain.first(), wordChain.last()), wordChain)
    }

    fun nextLayer(layer: Set<String>, seen: Set<String>): Set<String> {
        return layer
            .flatMap { Dictionary.wordsCloseTo(it) }
            .toSet()
            .minus(seen)
    }

    private val closeToWordsCache = Cache<String, List<String>>()
    private fun cachedWordsCloseTo(word: String): List<String> =
        closeToWordsCache.withCache(word, Dictionary::wordsCloseTo)
}