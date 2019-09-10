package uk.eleusis.et.wordchain

@Deprecated("Inefficient, use the direction-switching variant")
object ChainFinder {

    fun findChain(pair: Pair<String, String>): Chain {

        val layerSetsForward = findChain(setOf(pair.first), setOf(pair.second), setOf(pair.first))
        if (layerSetsForward != null) {
            // at least we know there is a chain
//            println("Found a chain of size ${layerSetsForward.size}")

            // Find corresponding backwards chain - shouldn't ever be null
            val layerSetsBackward = findChain(setOf(pair.second), setOf(pair.first), setOf(pair.second))!!

            // now we have to find an actual chain of words from it
            val chainStrings = findChainFromLayers(layerSetsForward.toList(), layerSetsBackward.toList())
            return Chain(pair, chainStrings)
        }

        throw NoChainFoundException()
    }

    private fun findChainFromLayers(layersForward: List<Set<String>>, layersBackward: List<Set<String>>): List<String> {
        if (layersForward.isEmpty() || layersBackward.isEmpty()) {
            return emptyList()
        }
        val currentForward = layersForward.first()
        val currentBackward = layersBackward.last()

        return listOf(currentForward.intersect(currentBackward).first()) +
                findChainFromLayers(layersForward.drop(1), layersBackward.dropLast(1))
    }

    /**
     * This checks for intersections between two sets.
     * If there is no intersection, it finds all possible words that could be next in the chain
     * and recurses to another level.
     * It returns a Set of all the layers it has found, unless it can't find anything in which
     * case it returns null.
     */
    private fun findChain(set1: Set<String>, set2: Set<String>, seen: Set<String>): Set<Set<String>>? {
        val intersect = set1.intersect(set2)
        if (intersect.isNotEmpty()) {
            return setOf(intersect)
        }

        // Find all possible next words
        val nextLevel1 = set1.map { Dictionary.wordsCloseTo(it) }
            .flatten()
            .toSet()
            .minus(seen)

        if (nextLevel1.isNotEmpty()) {
            val subChain = findChain(nextLevel1, set2, seen.plus(nextLevel1))
            if (subChain != null) {
                return setOf(set1) + subChain
            }
        }

        return null
    }

}

