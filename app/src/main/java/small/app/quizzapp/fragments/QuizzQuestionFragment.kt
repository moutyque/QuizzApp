package small.app.quizzapp.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import small.app.quizzapp.R
import small.app.quizzapp.databinding.QuizzQuestionFragmentBinding


class QuizzQuestionFragment : Fragment() {

    companion object {
        fun newInstance() = QuizzQuestionFragment()
    }

    private var correctAnswer: Int = 0
    private var selectedAnswer: Int = 0
    private var selectedFrame: FrameLayout? = null

    private lateinit var viewModel: QuizzQuestionViewModel
    private lateinit var binding: QuizzQuestionFragmentBinding
    val args: QuizzQuestionFragmentArgs by navArgs()
    var score: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = QuizzQuestionFragmentBinding.inflate(inflater)
        viewModel =
            ViewModelProvider(this, viewModelFactory { QuizzQuestionViewModel(args.index) }).get(
                QuizzQuestionViewModel::class.java
            )
        Log.i("QuizzQuestionFragment", "current score : " + args.score)
        correctAnswer = viewModel.currentQuestion.correctAnswer
        binding.btnSubmit.visibility = View.VISIBLE
        binding.btnNextQuestion.visibility = View.GONE

        setupFragment()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        // TODO: Use the ViewModel


    }

    private fun setupFragment() {
        score = args.score


        binding.tvQuestion.text = viewModel.currentQuestion.question

        val id = viewModel.currentQuestion.image
        binding.ivImage.setImageDrawable(getDrawable(id))


        binding.tvOptionOne.text = viewModel.currentQuestion.optionOne
        binding.frameOne.setOnClickListener { changeSelection(it) }

        binding.tvOptionTwo.text = viewModel.currentQuestion.optionTwo
        binding.frameTwo.setOnClickListener { changeSelection(it) }

        binding.tvOptionThree.text = viewModel.currentQuestion.optionThree
        binding.frameThree.setOnClickListener { changeSelection(it) }

        binding.tvOptionFour.text = viewModel.currentQuestion.optionFour
        binding.frameFour.setOnClickListener { changeSelection(it) }

        binding.btnSubmit.setOnClickListener {
            if (selectedFrame != null) {
                if (selectedAnswer == correctAnswer) {
                    score = args.score + 1
                }
                selectedFrame!!.background = getDrawable(R.drawable.option_border_bg_wrong)
                getFrameFromID(correctAnswer).background =
                    getDrawable(R.drawable.option_border_bg_correct)
                binding.btnSubmit.visibility = View.GONE
                binding.btnNextQuestion.visibility = View.VISIBLE
            }

        }

        binding.btnNextQuestion.setOnClickListener { view: View ->
            val nextIndex = args.index + 1
            if (nextIndex < viewModel.questionList.size) {
                val action =
                    QuizzQuestionFragmentDirections.actionQuizzQuestionFragmentSelf(args.name)
                action.score = score
                action.index = nextIndex
                view.findNavController()
                    .navigate(action)
            } else {
                val action =
                    QuizzQuestionFragmentDirections.actionQuizzQuestionFragmentToResultFragment(args.name)
                action.score = score
                action.maxScore = nextIndex
                view.findNavController()
                    .navigate(action)
            }


        }


        binding.progressBar.max = viewModel.questionList.size
        binding.progressBar.setProgress(args.index + 1)
        binding.tvProgress.text =
            getString(R.string.progress, args.index + 1, viewModel.questionList.size)
    }


    private fun changeSelection(view: View) {
//TODO : cannot do it once the submit button is clicked
        if (binding.btnSubmit.visibility == View.VISIBLE) {


            val frame: FrameLayout = view as FrameLayout
            Log.i("QuizzQuestionFragment", "You clicked on : " + view.id)
            binding.frameOne.background = getDefaultBackground()
            binding.frameTwo.background = getDefaultBackground()
            binding.frameThree.background = getDefaultBackground()
            binding.frameFour.background = getDefaultBackground()
            selectedFrame = view

            frame.background = getDrawable(R.drawable.option_border_bg_selected)

            when (frame) {
                binding.frameOne -> selectedAnswer = 1
                binding.frameTwo -> selectedAnswer = 2
                binding.frameThree -> selectedAnswer = 3
                binding.frameFour -> selectedAnswer = 4
                else -> selectedAnswer = 0
            }
        }

    }

    private fun getDrawable(id: Int): Drawable? {
        return ResourcesCompat.getDrawable(
            requireContext().getResources(),
            id,
            null
        )
    }

    private fun getDefaultBackground(): Drawable? {
        return getDrawable(R.drawable.default_option_border_bg)
    }

    private fun getFrameFromID(i: Int): FrameLayout {
        when (i) {
            1 -> return binding.frameOne
            2 -> return binding.frameTwo
            3 -> return binding.frameThree
            4 -> return binding.frameFour
            else -> throw Exception("Index do not exist : $i")
        }
    }

}