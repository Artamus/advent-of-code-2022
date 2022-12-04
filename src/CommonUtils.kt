import java.io.File

object InputReader {
    // Assumes input data file is in the same folder as this code file.
    private val inputData = InputReader::class.java.getResource("input-data-3.txt")!!
    val dataRows = File(inputData.toURI()).readLines()
}

fun readInput(fileName: String) = File("src", fileName).also { println(it.toURI()) }.readLines()