package day04

import readInput

fun main() {
    val inputData = readInput("input-data-4.txt")

    val assignmentPairs = inputData.map {
        parseAssignmentPair(it)
    }

    val numRedundantAssignments = assignmentPairs.count { it.isOneAssignmentContainedInOther }

    println("The number of paired assignments where one fully contains the other is $numRedundantAssignments")

    val numOverlappingAssignments = assignmentPairs.count { it.areAssignmentsOverlapping }

    println("The number of paired assignments where there is at least an overlap is $numOverlappingAssignments")

}

private fun parseAssignmentPair(assignmentPair: String): PairAssignments {
    val elfPairAssignments = assignmentPair.split(",").map { elfAssignment ->
        parseCleaningAssignment(elfAssignment)
    }

    if (elfPairAssignments.size != 2) throw IllegalArgumentException("The elves are assigned in pairs, but found a different number of elves: $elfPairAssignments")
    return PairAssignments(elfPairAssignments.first(), elfPairAssignments.last())
}

private fun parseCleaningAssignment(range: String): CleaningAssignment {
    val rangeValues = range.split("-")
    if (rangeValues.size != 2) throw IllegalArgumentException("The provided data should be should be a pair denoting a range, got: $rangeValues")

    val start = rangeValues.first().toInt()
    val endInclusive = rangeValues.last().toInt()

    return CleaningAssignment(start..endInclusive)
}

private data class PairAssignments(val firstElf: CleaningAssignment, val secondElf: CleaningAssignment) {
    val isOneAssignmentContainedInOther = firstElf.fullyContains(secondElf) || secondElf.fullyContains(firstElf)
    val areAssignmentsOverlapping = firstElf.overlaps(secondElf) || secondElf.overlaps(firstElf)
}

private data class CleaningAssignment(private val range: ClosedRange<Int>) : ClosedRange<Int> by range {
    fun overlaps(other: CleaningAssignment) = endInclusive >= other.start && endInclusive <= other.endInclusive
    fun fullyContains(other: CleaningAssignment) = endInclusive >= other.endInclusive && start <= other.start
}

