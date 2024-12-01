import kotlin.math.absoluteValue

fun List<String>.toLRLists(): Pair<List<Int>, List<Int>> {
    val left = mutableListOf<Int>()
    val right = mutableListOf<Int>()
    forEach { row ->
        if (row.isBlank()) return@forEach
        val (l, r) = row.split("   ")
        left.add(l.toInt())
        right.add(r.toInt())
    }
    return left to right
}

fun part1(content: List<String>): Int {
    val (left, right) = content.toLRLists()
    val rightSorted = right.sorted()
    val leftSorted = left.sorted()
    return leftSorted.mapIndexed { index, l ->
        val r = rightSorted[index]
        (r - l).absoluteValue
    }.sum()
}

fun part2(content: List<String>): Int {
    val (left, right) = content.toLRLists()
    val rightMap = right.groupBy { it }.mapValues { it.value.size }
    return left.sumOf { it * rightMap.getOrDefault(it, 0) }
}

fun main() {
    val input = readInput(day = 1, name = "data.txt").readLines()
    printResult(part1 = part1(input), part2 = part2(input))
}
