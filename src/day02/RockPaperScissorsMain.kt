package day02

import readInput

fun main() {
    val inputRows = readInput("input-data-2.txt")

    val totalScore = inputRows.map {
        parseRound(it)
    }.sumOf { it.score }

    println("Total score if playing according to the strategy guide is: $totalScore")

    val correctScore = inputRows.map {
        val data = it.split(" ")
        val opponentMove = parseEncryptedOpponentMove(data[0])
        val outcome = parseEncryptedOutcome(data[1])
        Round.fromMoveAndOutcome(opponentMove, outcome)
    }.sumOf { it.score }

    println("Total score if playing according to the strategy guide correctly is: $correctScore")
}

private fun parseRound(row: String): Round {
    val moves = row.split(" ")
    if (moves.size < 2) throw IllegalStateException("Every row should have at least 2 entries.")

    val opponentMove = parseEncryptedOpponentMove(moves[0])
    val playerMove = parseEncryptedPlayerMove(moves[1])

    return Round.fromMoves(opponentMove, playerMove)
}

private fun parseEncryptedOpponentMove(move: String) = when (move) {
    "A" -> Rock
    "B" -> Paper
    "C" -> Scissors
    else -> throw IllegalArgumentException("""Move "$move" does not map to anything.""")
}

private fun parseEncryptedPlayerMove(move: String) = when (move) {
    "X" -> Rock
    "Y" -> Paper
    "Z" -> Scissors
    else -> throw IllegalArgumentException("""Move "$move" does not map to anything.""")
}

private fun parseEncryptedOutcome(outcome: String) = when (outcome) {
    "X" -> Loss
    "Y" -> Draw
    "Z" -> Win
    else -> throw IllegalArgumentException("""day02.Outcome "$outcome" does not map to anything.""")
}
