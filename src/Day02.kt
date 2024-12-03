import kotlin.math.absoluteValue

@JvmInline
value class Distance(val value: Int) {
    private val isOutOfRange: Boolean get() = value.absoluteValue > 3
    val isIncreasing: Boolean get() = value < 0
    val isInRange: Boolean get() = !isOutOfRange
    val isValid: Boolean get() = value.absoluteValue > 0

    override fun toString(): String {
        return value.toString()
    }
}

@JvmInline
value class Level(private val value: Int) {
    operator fun minus(other: Level): Distance = Distance(value = value - other.value)
}
typealias Report = List<Level>

fun String.toReport(): Report = split(" ").map { Level(it.toInt()) }
fun Report.distances(): List<Distance> {
    val distances = mutableListOf<Distance>()
    for ((index, current) in withIndex()) {
        val next = getOrNull(index + 1) ?: continue
        distances.add(current - next)
    }
    return distances
}

fun Distance.isSafe(totalDistance: Distance): Boolean {
    return totalDistance.isIncreasing == isIncreasing && isInRange && isValid
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.count {
            val report = it.toReport()
            val totalDistance = report.first() - report.last()
            report.distances().all { it.isSafe(totalDistance) }
        }
    }

    fun part2(input: List<String>): Int {
        return input.count {
            val report = it.toReport()
            val totalDistance = report.first() - report.last()
            val distances = report.distances()
            val minDistances = distances.size - 1
            val validDistanceCount = distances.count { it.isSafe(totalDistance) }
            validDistanceCount >= minDistances
        }
    }

    "------------ Part 1".println()

    part1(listOf("7 6 4 2 1")).println()
    part1(listOf("1 2 7 8 9")).println()
    part1(listOf("9 7 6 2 1")).println()
    part1(listOf("1 3 2 4 5")).println()
    part1(listOf("8 6 4 4 1")).println()
    part1(listOf("1 3 6 7 9")).println()

    "------------ Part 2".println()

    part2(listOf("7 6 4 2 1")).println()
    part2(listOf("1 2 7 8 9")).println()
    part2(listOf("9 7 6 2 1")).println()
    part2(listOf("1 3 2 4 5")).println()
    part2(listOf("8 6 4 4 1")).println()
    part2(listOf("1 3 6 7 9")).println()

    val testData = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent().lines()

    "------------ Tests".println()

    part1(testData).also { check(it == 2) { "But was $it" } }
    part2(testData).also { check(it == 4) { "But was $it" } }

    "------------ Data".println()

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
