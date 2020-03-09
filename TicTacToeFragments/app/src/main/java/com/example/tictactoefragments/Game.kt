package com.example.tictactoefragments

data class Game(
    val player: MutableList<Char> = mutableListOf('X', 'O'),
    val moves: MutableList<Int> = mutableListOf())
{
    val winner: Boolean?
        get() {
            val p = listOf(
                moves.filterIndexed { index, _ -> index % 2 == 0 },
                moves.filterIndexed { index, _ -> index % 2 == 1 })

            for (i in p.indices) {
                p[i].apply {
                    //HORIZONTAL
                    if (containsAll(listOf(0, 1, 2))) return i != 0
                    if (containsAll(listOf(3, 4, 5))) return i != 0
                    if (containsAll(listOf(6, 7, 8))) return i != 0
                    //VERTICAL
                    if (containsAll(listOf(0, 3, 6))) return i != 0
                    if (containsAll(listOf(1, 4, 7))) return i != 0
                    if (containsAll(listOf(2, 5, 8))) return i != 0
                    //DIAGONAL
                    if (containsAll(listOf(0, 4, 8))) return i != 0
                    if (containsAll(listOf(2, 4, 6))) return i != 0

                }
            }
            return null
        }

    val draw: Boolean
        get() = moves.size == 9 && winner == null

    val end: Boolean
        get() = moves.size == 9 || winner != null

    val inProgress: Boolean
        get() = moves.size in 1..8 && winner == null

    val freshGame: Boolean
        get() = moves.isEmpty()

    fun newGame() {
        moves.clear()
    }

    fun move(i: Int): Boolean {
        if (i in moves) return false
        moves.add(i)
        return true
    }

    fun iToString(i: Int): String = when (i) {
        0 -> "Top Left"
        1 -> "Top"
        2 -> "Top Right"
        3 -> "Left"
        4 -> "Center"
        5 -> "Right"
        6 -> "Bottom Left"
        7 -> "Bottom"
        else -> "Bottom Right"
    }

    fun historyString(): String {
        var ret = ""
        moves.forEachIndexed { index, i ->
            ret += "${player[index % 2]} goes ${iToString(i)}\n"
        }
        ret += when (winner) {
            false -> "${player[0]} wins!"
            true -> "${player[1]} wins!"
            else -> "It's a draw!"
        }
        return ret
    }
}