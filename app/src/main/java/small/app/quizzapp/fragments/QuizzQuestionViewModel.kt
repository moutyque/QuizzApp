package small.app.quizzapp.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import small.app.quizzapp.data.Constants.getQuestions
import small.app.quizzapp.data.Question

class QuizzQuestionViewModel(val index: Int) : ViewModel() {
    var score: Int = 0
    val questionList = getQuestions()
    val currentQuestion: Question = questionList.get(index)

}

inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
    }