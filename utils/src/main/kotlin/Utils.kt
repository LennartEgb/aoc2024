import java.io.File
import kotlin.io.path.Path

fun readInput(day: Int, name: String): File = Path("day$day/src/main/resources/$name").toFile()

fun printResult(part1: Any, part2: Any) {
    println("Part 1: $part1")
    println("Part 2: $part2")
}
