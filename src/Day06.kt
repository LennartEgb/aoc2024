private enum class Direction(val char: Char) {
    Up('^'),
    Right('>'),
    Left('<'),
    Down('v');

    fun next(): Direction = when (this) {
        Up -> Right
        Right -> Down
        Down -> Left
        Left -> Up
    }
}

private typealias Field = Char

private data class Position(val x: Int, val y: Int) {
    fun next(direction: Direction): Position = when (direction) {
        Direction.Up -> Position(x = x, y = y - 1)
        Direction.Right -> Position(x = x + 1, y = y)
        Direction.Down -> Position(x = x, y = y + 1)
        Direction.Left -> Position(x = x - 1, y = y)
    }
}

private class Player(var position: Position, var direction: Direction) {
    infix fun canWalkOn(board: Board): Boolean =
        board.getOrNull(position.next(direction))?.let { it == '.' || it == 'X' } ?: false

    infix fun needsTurnOn(board: Board): Boolean = board.getOrNull(position.next(direction)) == '#'
    fun turn() {
        direction = direction.next()
    }

    infix fun walkOn(board: Board) {
        val nextPosition = position.next(direction)
        board[position] = 'X'
        board[nextPosition] = direction
        position = nextPosition
    }
}
private typealias Board = MutableList<CharArray>

private operator fun Board.get(position: Position): Field = this[position.y][position.x]
private fun Board.getOrNull(position: Position): Field? =
    this.getOrNull(position.y)?.getOrNull(position.x)

private operator fun Board.set(position: Position, field: Field) {
    this[position.y][position.x] = field
}

private operator fun Board.set(position: Position, direction: Direction) {
    this[position.y][position.x] = direction.char
}

private fun List<String>.getPositionAndDirection(): Pair<Position, Direction> {
    forEachIndexed { y, row ->
        row.forEachIndexed { x, char ->
            if (char in Direction.entries.map { it.char }) {
                return Position(x, y) to Direction.entries.first { it.char == char }
            }
        }
    }
    error("No initial position found")
}

fun main() {
    fun part1(input: List<String>): Int {
        val (position, direction) = input.getPositionAndDirection()
        val player = Player(position, direction)
        val board = input.map { it.toCharArray() }.toMutableList()
        while (player canWalkOn board) {
            player walkOn board
            if (player needsTurnOn board) player.turn()
        }
        return board.sumOf { array -> array.count { it == 'X' || it == player.direction.char } }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testData = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
    """.trimIndent().lines()
    part1(testData).apply { check(this == 41) { "But was $this" } }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
