package com.example.tictactoefragments

import androidx.lifecycle.ViewModel
import kotlin.random.Random
import kotlin.random.nextInt

class ListViewModel : ViewModel() {
    val games = mutableListOf<Game>()
    init {
        for (i in 1 until 15) {
            val g = Game()
            g.player[0] = Random.nextInt(65..90).toChar()
            g.player[1] = Random.nextInt(97..122).toChar()
            while (!g.end) {
                Random.nextInt(0..8).let {
                    if (it !in g.moves) g.moves.add(it)
                }
            }
            games += g
        }
    }
    val game = Game()
}