package day08

import readInput

fun main() {
    val inputData = readInput("input-data-8.txt")

    val parsed = inputData.map { row ->
        row.map { it.toString().toInt() }
    }

    val lastRow = parsed.size - 1
    val lastCol = parsed.first().size - 1

    val numVisibleInside = (1 until lastRow).product(1 until lastCol).count { (row, col) ->
        val rowVals = parsed[row]
        val colVals = extractColumn(parsed, col)

        visibleInList(rowVals, col) || visibleInList(colVals, row)
    }
    val numTotalVisible = numVisibleInside + 2 * parsed.size + 2 * (parsed.first().size - 2)

    println("Number of visible trees from outside the tree grid is $numTotalVisible")

    val biggestScenicValue = (1 until lastRow).product(1 until lastCol).maxOfOrNull { (row, col) ->
        val value = parsed[row][col]

        val rowVals = parsed[row]

        val left = rowVals.slice(0 until col).reversed().takeVisible(value)
        val right = rowVals.slice(col + 1 until rowVals.size).takeVisible(value)

        val colVals = extractColumn(parsed, col)
        val up = colVals.slice(0 until row).reversed().takeVisible(value)
        val down = colVals.slice(row + 1 until colVals.size).takeVisible(value)

        left.size * right.size * up.size * down.size
    }

    println("The biggest possible scenic value is $biggestScenicValue")
}

fun visibleInList(list: List<Int>, position: Int): Boolean {
    val value = list[position]

    return list.slice(0 until position).all { it < value } || list.slice(position + 1 until list.size)
        .all { it < value }
}

fun extractColumn(matrix: List<List<Int>>, col: Int) = matrix.map { it[col] }

fun List<Int>.takeVisible(value: Int): List<Int> {
    val list = ArrayList<Int>()

    for (item in this) {
        if (item >= value) {
            list.add(item)
            break
        } else {
            list.add(item)
        }
    }

    return list
}

fun IntRange.product(other: IntRange) = this.flatMap { row -> other.map { col -> row to col } }