package com.example.tictactoefragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

const val PLAYER1_SYMBOL = "com.example.labs.lab01.player1"
const val PLAYER2_SYMBOL = "com.example.labs.lab01.player2"

class ChooseFragment : Fragment() {

    private lateinit var chooseText: List<EditText>
    private lateinit var okButton: Button
    private lateinit var cancelButton: Button
    val viewModel: ListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_choose, container, false)

        chooseText = listOf(view.findViewById(R.id.choose_1), view.findViewById(R.id.choose_2))


//        chooseText[0].setText(activity!!.intent.getCharExtra(PLAYER1_SYMBOL, 'X').toString())
//        chooseText[1].setText(activity!!.intent.getCharExtra(PLAYER2_SYMBOL, 'X').toString())
        chooseText[0].setText(viewModel.game.player[0].toString())
        chooseText[1].setText(viewModel.game.player[1].toString())

        okButton = view.findViewById(R.id.ok_choose_button)
        cancelButton = view.findViewById(R.id.cancel_choose_button)

        okButton.isEnabled = false

        chooseText.forEach { c ->
//            c.setOnClickListener { c.text.clear() }
            c.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    okButton.isEnabled = !(chooseText[0].text.isEmpty() || chooseText[1].text.isEmpty() || chooseText[0].text.toString() == chooseText[1].text.toString())
                }
            })
        }


        okButton.setOnClickListener {
//            val data = Intent().apply {
//                putExtra(PLAYER1_SYMBOL, chooseText[0].text[0])
//                putExtra(PLAYER2_SYMBOL, chooseText[1].text[0])
//            }
//            activity!!.setResult(Activity.RESULT_OK, data)
//            activity!!.finish()

            viewModel.game.player[0] = chooseText[0].text[0]
            viewModel.game.player[1] = chooseText[1].text[0]
            view.findNavController().popBackStack()
//            view.findNavController().navigate(R.id.action_chooseFragment_to_gameFragment)
        }
        cancelButton.setOnClickListener {
//            activity!!.setResult(Activity.RESULT_CANCELED)
//            activity!!.finish()
//            view.findNavController().navigate(R.id.action_chooseFragment_to_gameFragment)
            view.findNavController().popBackStack()

        }
        
        
        return view
        
        
        
        
    }


}
