import java.io.File
import kotlin.io.path.Path

fun file(day: Int, name: String): File = Path("day$day/src/main/resources/$name").toFile()
