package day06

import readInput

fun main() {
    val inputData = readInput("input-data-6.txt").first()

    val startOfPacketMarkerEndIndex = findUniqueStreamEndIndex(inputData, 4)
    println("Last index for the sequence marking the start-of-packet is $startOfPacketMarkerEndIndex")

    val startOfMessagesMarkerEndIndex = findUniqueStreamEndIndex(inputData, 14)
    println("Last index for the sequence marking the start-of-message is $startOfMessagesMarkerEndIndex")
}

private fun findUniqueStreamEndIndex(dataStream: String, numUniqueCharacters: Int): Int =
    dataStream.windowedSequence(numUniqueCharacters).indexOfFirst { it.toSet().size == it.length } + numUniqueCharacters