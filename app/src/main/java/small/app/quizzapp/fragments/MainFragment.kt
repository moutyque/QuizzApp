package small.app.quizzapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import small.app.quizzapp.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MainFragmentBinding.inflate(inflater)
        binding.button.setOnClickListener { view: android.view.View ->
            if (!binding.name.text.isNullOrEmpty()) {
                val action =
                    MainFragmentDirections.actionMainFragmentToQuizzQuestionFragment(binding.name.text.toString())


                view.findNavController().navigate(action)
            } else {
                Toast.makeText(context, "Please enter a name", Toast.LENGTH_LONG).show()
            }
        }


        return binding.root

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}