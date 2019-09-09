package uk.eleusis.et.wordchain

/**
 * World's simplest cache. Will cache everything it sees, with no
 * care about memory / heap usage, beware!
 */
class Cache<T, V> {

    private val cache = HashMap<T, V>()

    fun withCache(key: T, block: (T) -> V): V {
        return cache.getOrElse(key) {
            val entry = block(key)
            cache[key] = entry
            return entry
        }
    }
}
