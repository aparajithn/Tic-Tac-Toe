package com.example.tictactoefragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment() {

//    private val viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

//    private val viewModel: ListViewModel by lazy {
//        ViewModelProvider(this).get(ListViewModel::class.java)
//    }
    val viewModel: ListViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private var adapter: GameAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view.findViewById(R.id.game_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }

    private fun updateUI() {
        val games = viewModel.games
        adapter = GameAdapter(games)
        recyclerView.adapter = adapter
    }

    private inner class GameHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var game: Game

        val winner: TextView = itemView.findViewById(R.id.game_winner)
        val moves: TextView = itemView.findViewById(R.id.game_moves)

        fun bind(game: Game) {
            this.game = game
            winner.text = "${when (game.winner) {
                true -> "${game.player[0]} < ${game.player[1]}"
                false -> "${game.player[0]} > ${game.player[1]}"
                else -> "${game.player[0]} = ${game.player[1]}"
            }}"
            moves.text = game.moves.toString()
        }

        init {
            itemView.setOnClickListener {
                itemView.findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment(layoutPosition))
            }
        }


    }

    private inner class GameAdapter(var games: List<Game>) : RecyclerView.Adapter<GameHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
            val view = layoutInflater.inflate(R.layout.list_item_game, parent, false)
            return GameHolder(view)
        }

        override fun getItemCount() = games.size


        override fun onBindViewHolder(holder: GameHolder, position: Int) {
            val game = games[position]
            holder.bind(game)
        }

    }

}