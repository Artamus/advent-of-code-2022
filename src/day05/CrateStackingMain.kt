package day05

import readInput

fun main() {
    val inputData = readInput("input-data-5.txt")

    val startingState = inputData.takeWhile { it.isNotBlank() }.asReversed()
    val crates = buildCrateStorage(startingState)

    val moves = inputData
        .takeLastWhile { it.isNotBlank() }
        .map { parseMove(it) }


    for (move in moves) {
        crates.apply(move)
    }

    println("After applying the moves, the topmost crates are ${crates.topMostCrates.joinToString("")}")

    val secondCrates = buildCrateStorage(startingState)
    for (move in moves) {
        secondCrates.applyChunked(move)
    }

    println("After applying moves in a chunked way, the topmost crates are ${secondCrates.topMostCrates.joinToString("")}")
}

private fun parseMove(input: String): Move {
    val whitespaceSeparated = input
        .replace("move ", "")
        .replace("from ", "")
        .replace("to ", "")
    val values = whitespaceSeparated
        .split(" ")
        .map(String::toInt)

    return Move(values[0], values[1] - 1, values[2] - 1)
}

private fun buildCrateStorage(input: List<String>): CrateStorage {
    val numStacks = input.first().chunked(4).size
    val crates = CrateStorage.ofSize(numStacks)

    input.drop(1).map {
        it.chunked(4).forEachIndexed { index, rawCrate ->
            val crate = parseCrate(rawCrate)
            crate?.let { crates[index].addLast(crate) }
        }
    }

    return crates
}

private fun parseCrate(input: String): Char? {
    val crateValue = input
        .replace(" ", "")
        .replace("[", "")
        .replace("]", "")
    if (crateValue.length > 1) throw IllegalArgumentException("Malformed input, expected parsing result to have 1 character, got $crateValue")

    return if (crateValue.isNotBlank()) {
        crateValue.first()
    } else {
        null
    }
}
