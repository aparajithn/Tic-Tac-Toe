package com.example.tictactoefragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController

private const val REQUEST_CODE_CHOOSE = 0


class GameFragment : Fragment() {


    private lateinit var grid: List<Button>
    private lateinit var chooseButton: Button
        val viewModel: ListViewModel by activityViewModels()
//    private val viewModel: GameViewModel by lazy {
//        ViewModelProviders.of(this).get(GameViewModel::class.java)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view.hashCode()

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game, container, false)


        grid = listOf(
            view.findViewById(R.id.cell_1),
            view.findViewById(R.id.cell_2),
            view.findViewById(R.id.cell_3),
            view.findViewById(R.id.cell_4),
            view.findViewById(R.id.cell_5),
            view.findViewById(R.id.cell_6),
            view.findViewById(R.id.cell_7),
            view.findViewById(R.id.cell_8),
            view.findViewById(R.id.cell_9)
        )

        savedInstanceState?.getIntArray("MOVES")
            ?.let { it ->
                viewModel.game.moves.clear()
                viewModel.game.moves.addAll(it.toList())
            }

        savedInstanceState?.getCharArray("PLAYERS")
            ?.let { it ->
                viewModel.game.player.clear()
                viewModel.game.player.addAll(it.toList())
            }

        chooseButton = view.findViewById(R.id.choose_button)


//        chooseButton.setOnClickListener {
//            val intent = Intent(this.context, ChooseFragment::class.java).apply {
//                putExtra(PLAYER1_SYMBOL, viewModel.game.player[0])
//                putExtra(PLAYER2_SYMBOL, viewModel.game.player[1])
//            }
//            startActivityForResult(intent, REQUEST_CODE_CHOOSE)
//        }

        chooseButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_gameFragment_to_chooseFragment))

        for (i in grid.indices) {
            val g = grid[i]
            g.setOnClickListener {
                if (!viewModel.game.end && viewModel.game.move(i)) {
                    if (viewModel.game.end) {
                        endMessage()
                    }
                    drawBoard()
                }
            }
        }

        drawBoard()

        return view
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode != Activity.RESULT_OK) return
//        if (requestCode == REQUEST_CODE_CHOOSE) {
//            viewModel.game.player[0] = data?.getCharExtra(PLAYER1_SYMBOL, 'X') ?: 'X'
//            viewModel.game.player[1] = data?.getCharExtra(PLAYER2_SYMBOL, 'O') ?: 'O'
//            drawBoard()
//        }
//    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntArray("MOVES", viewModel.game.moves.toIntArray())
        outState.putCharArray("PLAYERS", viewModel.game.player.toCharArray())
    }

    private fun newGame() {
        viewModel.game.newGame()
        drawBoard()
    }

    private fun drawBoard() {
        grid.forEach { it.text = "" }
        viewModel.game.moves.forEachIndexed { index, i ->
            grid[i].text = viewModel.game.player[index % 2].toString()
        }
        chooseButton.isEnabled = viewModel.game.freshGame
    }

    private fun endMessage() {
//        val message: String = when (viewModel.game.winner) {
//            false -> "${viewModel.game.player[0]} wins!"
//            true -> "${viewModel.game.player[1]} wins!"
//            null -> "It's a draw!"
//        }
//        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        viewModel.games.add(0, Game(viewModel.game.player.toMutableList(), viewModel.game.moves.toMutableList()))
//        viewModel.game.newGame()
        view?.findNavController()?.navigate(
            when (viewModel.game.winner) {
                false -> R.id.action_gameFragment_to_winFragment
                else -> R.id.action_gameFragment_to_loseFragment
//            else -> null
            }
        )
    }


}
