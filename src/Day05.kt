private data class Rule(val current: Int, val next: Int) : Comparable<Rule> {
    override fun compareTo(other: Rule): Int {
        TODO("Not yet implemented")
    }

}
private typealias Rules = List<Rule>
private typealias Sequence = List<Int>

private fun String.toNode(): Rule =
    split("|").let { (first, second) -> Rule(first.toInt(), second.toInt()) }

private fun String.toInts(delimiter: String): List<Int> = split(delimiter).map { it.toInt() }
private fun Sequence.isValid(rules: Rules): Boolean = toRules().all { it in rules }
private fun <T> List<T>.centredValue(): T = get(size / 2)
private fun List<Int>.toRules(): List<Rule> =
    windowed(2, partialWindows = false) { (first, second) -> Rule(first, second) }

private fun List<String>.rulesToSequences(): Pair<List<Rule>, List<Sequence>> {
    val delimiter = "\n"
    val (_rules, _sequences) = joinToString(delimiter).split(delimiter.repeat(2))
    val rules = _rules.split(delimiter).map { it.toNode() }
    val sequences = _sequences.split(delimiter).map { it.toInts(",") }
    return rules to sequences
}

private fun Sequence.fix(rules: Rules): Sequence {
    val list = this
    return buildList {
        var current = list.first()
        add(current)
        while (size < list.size) {
            val next = rules.firstOrNull { it.current == current && it.next in list }?.next
                ?: return list.subList(1, list.size).plus(list.first()).fix(rules)
            add(next)
            current = next
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val (rules, sequences) = input.rulesToSequences()
        return sequences.filter { it.isValid(rules) }.sumOf { it.centredValue() }
    }

    fun part2(input: List<String>): Int {
        val (rules, sequences) = input.rulesToSequences()
        val invalids = sequences.filterNot { it.isValid(rules) }.apply { println() }
        return invalids.map { it.fix(rules) }.apply { println() }
            .map { it.centredValue() }.apply { println() }
            .sum()
    }

    val testData = """
        47|53
        97|13
        97|61
        97|47
        75|29
        61|13
        75|53
        29|13
        97|29
        53|29
        61|53
        97|53
        61|29
        47|13
        75|47
        97|75
        47|61
        75|61
        47|29
        75|13
        53|13

        75,47,61,53,29
        97,61,53,29,13
        75,29,13
        75,97,47,61,53
        61,13,29
        97,13,75,29,47
    """.trimIndent().lines()

    "-------------TEST".println()
    part1(testData).apply { check(this == 143) { "But was $this" } }
    part2(testData).apply { check(this == 123) { "But was $this" } }
    "-------------DATA".println()
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
