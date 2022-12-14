package day09

import readInput
import kotlin.math.abs

fun main() {
    val inputData = readInput("input-data-9.txt")

    val initialState = State(head = Position(0, 0), tail = Position(0, 0))

    val allStates = inputData.map {
        parseMove(it)
    }.fold(listOf(initialState)) { stateHistory, move ->
        val currentState = stateHistory.last()
        stateHistory + currentState.apply(move)
    }

    val uniqueTailPositions = allStates.map { it.tail }.distinct().count()
    println("Tail visited $uniqueTailPositions unique positions.")

    val initialState2 =
        State2(head = Knot.Element(position = Position(0, 0), next = Knot.Tail(position = Position(0, 0))))
    val bla = inputData.map {
        parseMove(it)
    }.fold(listOf(initialState2)) { stateHistory, move ->
        val currentState = stateHistory.last()
        stateHistory + currentState.apply(move)
    }
    println(bla.map { it.getTail() }.distinct().count())

    val initialState3 = State2(head = createRope(10) as Knot.Element)
    val stuff = inputData.map { parseMove(it) }.fold(listOf(initialState3)) { stateHistory, move ->
        val currentState = stateHistory.last()
        println("Current state: $currentState")
        println("Applying move $move")
        stateHistory + currentState.apply(move)
    }

    println(stuff.last())
    println(stuff.size)
    println(stuff.map { it.getTail() }.distinct().count())
}

private fun createRope(length: Int): Knot {
    val startingPosition = Position(0, 0)

    if (length == 1) {
        return Knot.Tail(position = startingPosition)
    }

    val nextKnot = createRope(length - 1)

    return Knot.Element(position = startingPosition, next = nextKnot)
}

data class State(val head: Position, val tail: Position) {

    tailrec fun apply(move: Move, moveHistory: List<State> = listOf()): List<State> {
        if (moveHistory.size == move.numSteps) {
            return moveHistory
        }

        val currentState = if (moveHistory.isNotEmpty()) moveHistory.last() else this

        val newHead = currentState.head + move
        val currentTail = currentState.tail

        val newTail = if (currentTail.isTooFar(newHead)) {
            currentTail.moveCloserTo(newHead)
        } else {
            currentTail
        }

        return apply(move, moveHistory + listOf(State(newHead, newTail)))
    }
}

data class State2(val head: Knot.Element) {

    tailrec fun apply(move: Move, moveHistory: List<State2> = listOf()): List<State2> {
        if (moveHistory.size == move.numSteps) {
            return moveHistory
        }

        val currentState = if (moveHistory.isNotEmpty()) moveHistory.last() else this
        // println("Current move made ${moveHistory.size} times, state: $currentState")

        val newHeadPosition = currentState.head.position + move

        val newHead = Knot.Element(position = newHeadPosition, updateRope(newHeadPosition, currentState.head.next))

        return apply(move, moveHistory + listOf(State2(newHead)))
    }

    tailrec fun getTail(node: Knot = head): Knot.Tail = when (node) {
        is Knot.Tail -> node
        is Knot.Element -> getTail(node.next)
    }
}

fun updateRope(parentNewPosition: Position, knot: Knot): Knot {
    val newKnotPosition = if(knot.position.isTooFar(parentNewPosition)) {
        knot.position.moveCloserTo(parentNewPosition)
    } else {
        knot.position
    }

    return when (knot) {
        is Knot.Tail -> Knot.Tail(newKnotPosition)
        is Knot.Element -> {
            val updatedNext = updateRope(newKnotPosition, knot.next)
            Knot.Element(newKnotPosition, updatedNext)
        }
    }
}

sealed class Knot {
    abstract val position: Position

    data class Element(override val position: Position, val next: Knot) : Knot()
    data class Tail(override val position: Position) : Knot()
}

data class Position(val row: Int, val col: Int) {
    operator fun plus(move: Move) = Position(row + move.movement.first, col + move.movement.second)

    fun isTooFar(other: Position): Boolean = abs(row - other.row) > 1 || abs(col - other.col) > 1

    fun moveCloserTo(other: Position): Position {
        require(totalManhattanDistanceTo(other) <= 4) { "Got total distance more than 3 for $this and $other"}

        val rowDiff = (other.row - this.row).coerceIn(-1, 1)
        val colDiff = (other.col - this.col).coerceIn(-1, 1)

        return Position(row + rowDiff, col + colDiff)
    }

    private fun totalManhattanDistanceTo(other: Position) = abs(row - other.row) + abs(col - other.col)
}

sealed class Move(val movement: Pair<Int, Int>) {
    abstract val numSteps: Int
}

data class Up(override val numSteps: Int) : Move(1 to 0)
data class Down(override val numSteps: Int) : Move(-1 to 0)
data class Left(override val numSteps: Int) : Move(0 to -1)
data class Right(override val numSteps: Int) : Move(0 to 1)

fun parseMove(rawInput: String): Move {
    val parts = rawInput.split(" ")
    require(parts.size == 2) { "Input must contain direction and magnitude." }
    val magnitude = parts[1].toInt()
    return when (parts.first()) {
        "U" -> Up(magnitude)
        "D" -> Down(magnitude)
        "L" -> Left(magnitude)
        "R" -> Right(magnitude)
        else -> throw IllegalArgumentException("Unknown direction \"${parts.first()}\"")
    }
}