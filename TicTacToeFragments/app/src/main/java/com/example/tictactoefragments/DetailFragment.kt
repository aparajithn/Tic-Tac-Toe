package com.example.tictactoefragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController

class DetailFragment : Fragment() {

    val viewModel: ListViewModel by activityViewModels()
    private lateinit var grid: List<Button>
    private lateinit var deleteButton: Button
    private var position: Int = 0
    private lateinit var game: Game

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        position = DetailFragmentArgs.fromBundle(arguments!!).position
        game = viewModel.games[position]

        deleteButton = view.findViewById(R.id.delete_button)
        deleteButton.setOnClickListener {
            viewModel.games.removeAt(position)
            view.findNavController().popBackStack()
        }

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

        drawBoard()

        return view
    }

    private fun drawBoard() {
        grid.forEach { it.text = "" }
        game.moves.forEachIndexed { index, i ->
            grid[i].text = game.player[index % 2].toString()
        }
    }
}
