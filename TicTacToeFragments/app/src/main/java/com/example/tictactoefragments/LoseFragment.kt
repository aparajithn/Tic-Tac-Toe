package com.example.tictactoefragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_win.*

/**
 * A simple [Fragment] subclass.
 */
class LoseFragment : Fragment() {

    private lateinit var shareButton: Button
    val viewModel: ListViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lose, container, false)
        // Inflate the layout for this fragment
        shareButton = view.findViewById(R.id.share_button)
        val endMessage = view.findViewById(R.id.end_string) as TextView
        endMessage.text = getString(R.string.win_message, viewModel.game.player[1])

        shareButton.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, viewModel.game.historyString())
            }.also { intent ->

                val chooserIntent =
                    Intent.createChooser(
                        intent,
                        "Share ${when (viewModel.game.winner) {
                            false -> "${viewModel.game.player[0]}'s"
                            true -> "${viewModel.game.player[1]}'s"
                            null -> "draw"
                        }} game!"
                    )
                startActivity(chooserIntent)
            }
        }

        return view
    }


}
