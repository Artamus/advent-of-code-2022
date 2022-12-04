package day01

import readInput

fun main() {
    val inputData = readInput("input-data-1.txt")

    val elfTotalCalories = getElfCalories(inputData).map { it.sum() }

    val mostCalories = elfTotalCalories.max()
    println("Most calories: $mostCalories")

    val topThreeCalories = elfTotalCalories.sortedDescending().take(3)
    println("Top three calories total: ${topThreeCalories.sum()}")
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