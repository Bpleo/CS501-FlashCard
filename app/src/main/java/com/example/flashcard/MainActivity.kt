package com.example.flashcard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.flashcard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val questionViewModel: QuestionViewModel by viewModels()
    private var correctCount: Int = 0
    private var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.generateBtn.setOnClickListener {
            questionViewModel.rePopulateList()
            updateQuestion()
            correctCount = 0
            count = 0
        }
        binding.submitBtn.setOnClickListener {
            checkAnswer(it)
        }
    }

    private fun updateQuestion(){
        binding.num1.text = questionViewModel.currentOperand1.toString()
        binding.num2.text = questionViewModel.currentOperand2.toString()
        if (questionViewModel.currentOperator == 1)
            binding.operator.text = "+"
        else
            binding.operator.text = "-"
        count++
    }

    private fun checkAnswer(view: View) {
        val enteredAns: String = binding.answerET.toString()
        if (enteredAns.compareTo(questionViewModel.currentAnswer.toString()) == 0)
            correctCount++
        questionViewModel.moveToNext()
        updateQuestion()
        val char = correctCount.toChar() + "/" + count.toString()
        if (count == 10){
            Toast.makeText(
                view,
                char,
                Toast.LENGTH_LONG
            ).show()
        }
    }

}