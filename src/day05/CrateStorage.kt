package day05

data class CrateStorage(private val crates: List<ArrayDeque<Char>>) : List<ArrayDeque<Char>> by crates {

    val topMostCrates: List<Char>
        get()
        = crates.map { it.last() }

    fun apply(move: Move) {
        repeat(move.numCrates) {
            val item = crates[move.fromStack].removeLast()
            crates[move.toStack].addLast(item)
        }
    }

    fun applyChunked(move: Move) {
        val elements = ArrayDeque<Char>()
        repeat(move.numCrates) {
            elements.addLast(crates[move.fromStack].removeLast())
        }

        repeat(elements.size) {
            crates[move.toStack].addLast(elements.removeLast())
        }
    }

    companion object {
        fun ofSize(n: Int) = CrateStorage(List(n) { ArrayDeque() })
    }
}