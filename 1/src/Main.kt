import java.io.File

object InputReader {
    // Assumes input data file is in the same folder as this code file.
    private val inputData = InputReader::class.java.getResource("input-data.txt")!!
    val dataLines = File(inputData.toURI()).readLines()
}

fun main() {
    val data: MutableList<MutableList<Int>> = mutableListOf(mutableListOf())
    val elfCalories = InputReader.dataLines.fold(data) { list, element ->
        if (element.isBlank()) {
            list.add(mutableListOf())
        } else {
            list.last().add(element.toInt())
        }
        list
    }.map { it.sum() }

    val mostCalories = elfCalories.max()
    println("Most calories: $mostCalories")

    val topThreeCalories = elfCalories.sortedDescending().take(3)
    println("Top three calories total: ${topThreeCalories.sum()}")
}
