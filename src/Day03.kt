sealed interface Instruction {

    companion object {
        private val mulRegex = """mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\)""".toRegex()
        fun findAll(value: String): List<Instruction> = mulRegex.findAll(input = value)
            .map { Instruction(it.value) }.toList()

        operator fun invoke(value: String): Instruction {
            return when {
                value.startsWith("don") -> Dont
                value.startsWith("do") -> Do
                value.startsWith("mul") -> Mul(value)
                else -> error("Unsupported instruction: $value")
            }
        }
    }

    data object Do : Instruction
    data object Dont : Instruction

    @JvmInline
    value class Mul(private val value: String) : Instruction {
        val result: Long
            get() = value.removeSurrounding(prefix = "mul(", suffix = ")").split(",")
                .map { it.toLong() }
                .reduce { acc, i -> acc * i }
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        return input.map(Instruction::findAll)
            .sumOf { it.filterIsInstance<Instruction.Mul>().sumOf { it.result } }
    }

    fun part2(input: List<String>): Long {
        val results = input.map {
            val instructions = Instruction.findAll(it)
            var enabled = true
            var sum = 0L
            for (instruction in instructions) {
                when (instruction) {
                    Instruction.Dont -> enabled = false
                    Instruction.Do -> enabled = true
                    is Instruction.Mul -> {
                        if (enabled) {
                            sum += instruction.result
                        }
                    }
                }
            }
            sum
        }
        return results.sum()
    }

    check(part1(listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")) == 161L)
    check(part2(listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")) == 48L)

    val input = readInput("Day03").joinToString("\n")
    part1(listOf(input)).println()
    part2(listOf(input)).println()
}
