package uk.eleusis.et.wordchain

import java.util.stream.Collectors

object ChainFinderSwitching {

    private data class ChainFinderContext(
        val set1: Set<String>,
        val set2: Set<String>,
        val seenForward: Set<String>,
        val seenBackward: Set<String>
    )

    fun findShortestChain(pair: Pair<String, String>): Chain {

        val ctx = ChainFinderContext(setOf(pair.first), setOf(pair.second), setOf(pair.first), setOf(pair.second))
        val layerSets = findLayers(ctx, this::findLayersForwards)
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
        ctx: ChainFinderContext,
        findLayerFunction: (ChainFinderContext) -> List<Set<String>>?
    ): List<Set<String>>? {

        // If there's an overlap, we're done
        val intersect = ctx.set1 intersect ctx.set2
        if (intersect.isNotEmpty()) {
            return listOf(intersect)
        }

        // No overlap yet, try another layer
        return findLayerFunction(ctx)
    }

    private fun findLayersForwards(ctx: ChainFinderContext): List<Set<String>>? {
        val (set1, set2, seenForward, seenBackward) = ctx
        val nextLevel = nextLayer(set1, seenForward)

        if (nextLevel.isNotEmpty()) {
            val newCtx = ChainFinderContext(nextLevel, set2, seenForward.plus(nextLevel), seenBackward)
            val subChain = findLayers(newCtx, this::findLayersBackwards)

            if (subChain != null) {
                val potentials = subChain.first().flatMap { cachedWordsCloseTo(it) }
                val validWords = set1 intersect potentials
                return listOf(validWords) + subChain
            }
        }

        return null
    }

    private fun findLayersBackwards(ctx: ChainFinderContext): List<Set<String>>? {
        val (set1, set2, seenForward, seenBackward) = ctx
        val nextLevel = nextLayer(set2, seenBackward)

        if (nextLevel.isNotEmpty()) {
            val newCtx = ChainFinderContext(set1, nextLevel, seenForward, seenBackward.plus(nextLevel))
            val subChain = findLayers(newCtx, this::findLayersForwards)

            if (subChain != null) {
                val potentials = subChain.last().flatMap { cachedWordsCloseTo(it) }
                val validWords = set2 intersect potentials
                return subChain + listOf(validWords)
            }
        }

        return null
    }

    /**
     * Find all possible next words, excluding ones we've already seen
     */
    fun nextLayer(layer: Set<String>, seen: Set<String>): Set<String> {
        return layer.parallelStream()
            .flatMap { cachedWordsCloseTo(it).parallelStream() }
            .filter { !seen.contains(it) }
            .collect(Collectors.toSet())
    }

    /**
     * Helper function for generating challenge entries - can find really long chains
     * (not used in main flow)
     */
    fun findLongestChainForWord(word: String): Chain {
        var layers: List<Set<String>> = listOf(setOf(word))
        var seen = emptySet<String>()
        var done = false
        while (!done) {
            seen = seen + layers.last()
            val next = nextLayer(layers.last(), seen)
            if (next.isNotEmpty()) {
                layers = layers + listOf(next) // for some reason Kotlin can't correctly infer when I pass just 'next'
            } else {
                done = true
                println()
            }
        }

        val wordChain = findChainFromLayers(layers.reversed()).reversed()
        return Chain(Pair(wordChain.first(), wordChain.last()), wordChain)
    }

    private val closeToWordsCache = Cache<String, List<String>>()
    private fun cachedWordsCloseTo(word: String): List<String> =
        closeToWordsCache.withCache(word, Dictionary::wordsCloseTo)
}