package uk.eleusis.et.wordchain

data class Chain(val pair: Pair<String, String>, val words: List<String>)

class NoChainFoundException : Exception()

