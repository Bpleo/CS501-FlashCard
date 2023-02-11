package com.example.flashcard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.flashcard.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val questionViewModel: QuestionViewModel by viewModels()
    private var correctCount: Int = 0
    private var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.submitBtn.isEnabled = false
        binding.answerET.isCursorVisible = false
        binding.generateBtn.setOnClickListener {
            questionViewModel.rePopulateList()
            updateQuestion()
            correctCount = 0
            count = 0
            binding.submitBtn.isEnabled = true
            binding.generateBtn.isEnabled = false
        }
        binding.submitBtn.setOnClickListener { view: View ->
            val enteredAns: String = binding.answerET.getText().toString()
            binding.answerET.setText("")
            if (enteredAns.compareTo(questionViewModel.currentAnswer.toString()) == 0) {
                correctCount++
                Snackbar.make(view,"Correct",Toast.LENGTH_SHORT).show()
            } else {
                Snackbar.make(view,"Incorrect",Toast.LENGTH_SHORT).show()
            }
                questionViewModel.moveToNext()
                updateQuestion()
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
        Log.i("COUNT",count.toString())
        Log.i("CCOUNT",correctCount.toString())
        if (count == 10) {
            Toast.makeText(
                this,
                "$correctCount/$count",
                Toast.LENGTH_SHORT
            ).show()
            reset()
        }
    }

    private fun reset(){
        binding.submitBtn.isEnabled = false
        binding.generateBtn.isEnabled = true
        binding.num1.text = "*"
        binding.num2.text = "*"
        binding.operator.text = "+/-"
    }
}