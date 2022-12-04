package day03

data class Rucksack(private val firstCompartment: List<Char>, private val secondCompartment: List<Char>) {

    private val uniqueInFirst = firstCompartment.toSet()
    private val uniqueInSecond = secondCompartment.toSet()

    val commonItemsInBothCompartments = uniqueInFirst intersect uniqueInSecond
    val uniqueItems = uniqueInFirst + uniqueInSecond

    companion object {
        fun from(input: String): Rucksack {
            val numberOfItems = input.length
            val halfwayIndex = numberOfItems / 2

            val firstCompartment = input.slice(0 until halfwayIndex)
            val secondCompartment = input.slice(halfwayIndex until numberOfItems)

            return Rucksack(firstCompartment.toList(), secondCompartment.toList())
        }
    }
}