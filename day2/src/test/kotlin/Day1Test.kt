import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Test {
    @Test
    fun part1() {
        val data = """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
        """.trimIndent()
        assertEquals(expected = 11, actual = part1(data.lines()))
    }

    @Test
    fun part2() {
        val data = """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
        """.trimIndent()
        assertEquals(expected = 31, actual = part2(data.lines()))
    }
}
