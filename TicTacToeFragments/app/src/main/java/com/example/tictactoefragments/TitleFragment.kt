package com.example.tictactoefragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController

class TitleFragment : Fragment() {

    private lateinit var playButton: Button
    private lateinit var historyButton: Button

    val viewModel: ListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_title, container, false)
        playButton = view.findViewById(R.id.play_button)
        playButton.setOnClickListener {
            viewModel.game.newGame()
            view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
        }
        historyButton = view.findViewById(R.id.history_button)
        historyButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_titleFragment_to_listFragment)
        }

        return view
    }


}
