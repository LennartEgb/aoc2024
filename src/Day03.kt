sealed interface Instruction {

    companion object {
        private val mulRegex = """(?:mul\(\d+,\d+\)|do\(\)|don't\(\))""".toRegex()
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
        val result: Int
            get() = value.substringAfter("mul(").substringBefore(")").split(",").map { it.toInt() }
                .reduce { acc, i -> acc * i }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.map(Instruction::findAll)
            .sumOf { it.filterIsInstance<Instruction.Mul>().sumOf { it.result } }
    }

    fun part2(input: List<String>): Int {
        val results = input.map {
            val instructions = Instruction.findAll(it)
            var enabled = true
            var result = 0
            for (instruction in instructions) {
                when (instruction) {
                    Instruction.Dont -> enabled = false
                    Instruction.Do -> enabled = true
                    is Instruction.Mul -> {
                        if (enabled) {
                            result += instruction.result
                        }
                    }
                }
            }
            result
        }
        return results.sum()
    }

    check(part1(listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")) == 161)
    check(part2(listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")) == 48)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
