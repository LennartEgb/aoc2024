@JvmInline
value class StringGrid(private val value: List<String>) {
    init {
        require(value.isNotEmpty())
        require(value.first().isNotEmpty())
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testData = """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX
    """.trimIndent().lines()

    val result = part1(testData)
    check(result == 18)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
