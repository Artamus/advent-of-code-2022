package org.artamus.adventofcode

import java.io.File

fun main() {
    val elfTotalCalories = getElfCalories(InputReader.dataRows).map { it.sum() }

    val mostCalories = elfTotalCalories.max()
    println("Most calories: $mostCalories")

    val topThreeCalories = elfTotalCalories.sortedDescending().take(3)
    println("Top three calories total: ${topThreeCalories.sum()}")
}

object InputReader {
    // Assumes input data file is in the same folder as this code file.
    private val inputData = InputReader::class.java.getResource("input-data.txt")!!
    val dataRows = File(inputData.toURI()).readLines()
}

private fun getElfCalories(rows: List<String>): List<List<Int>> {
    val calories: MutableList<MutableList<Int>> = mutableListOf(mutableListOf())
    for (row in rows) {
        if (row.isBlank()) {
            calories.add(mutableListOf())
        } else {
            calories.last().add(row.toInt())
        }
    }
    return calories
}