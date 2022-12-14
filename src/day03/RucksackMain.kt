package day03

import readInput

fun main() {
    val inputRows = readInput("input-data-3.txt")

    val totalPriorityOfDuplicates = inputRows.sumOf {
        val rucksack = Rucksack.from(it)
        calculatePriorityOfDuplicateItems(rucksack)
    }

    println("Total priority of per rucksack duplicates is $totalPriorityOfDuplicates")

    val totalPriorityOfBadges = inputRows.chunked(3).sumOf {
        val firstRucksack = Rucksack.from(it[0])
        val secondRucksack = Rucksack.from(it[1])
        val thirdRucksack = Rucksack.from(it[2])
        calculatePriorityOfBadgeItem(firstRucksack, secondRucksack, thirdRucksack)
    }

    println("Total priority of elf band badge items is $totalPriorityOfBadges")
}

private fun calculatePriorityOfDuplicateItems(rucksack: Rucksack): Int {
    val itemsInBoth = rucksack.commonItemsInBothCompartments
    if (itemsInBoth.size > 1) throw IllegalStateException("There is only supposed to be one overlapping item in both compartments. Found: $itemsInBoth")
    val item = itemsInBoth.first()

    return calculatePriority(item)
}

private fun calculatePriorityOfBadgeItem(first: Rucksack, second: Rucksack, third: Rucksack): Int {
    val commonItems = first.uniqueItems intersect second.uniqueItems intersect third.uniqueItems
    if (commonItems.size > 1) throw IllegalStateException("There is only supposed to be one badge item. Found $commonItems")
    val badgeItem = commonItems.first()

    return calculatePriority(badgeItem)
}

private fun calculatePriority(char: Char): Int {
    val lowerCaseStartChar = 'a'
    val upperCaseStartChar = 'A'

    return if (char.isUpperCase()) char.code - upperCaseStartChar.code + 27
    else char.code - lowerCaseStartChar.code + 1
}