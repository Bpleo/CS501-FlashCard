package com.example.flashcard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.random.Random

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val CURRENT_SUBMIT_STATUS = "CURRENT_SUBMIT_STATUS"

class QuestionViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val questionList = ArrayList<Question>();

    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    val currentOperand1: Int
        get() = questionList[currentIndex].operand1
    val currentOperand2: Int
        get() = questionList[currentIndex].operand2
    val currentOperator: Int // 1 for plus, 2 for subtract
        get() = questionList[currentIndex].operator
    val currentAnswer: Int
        get() = questionList[currentIndex].answer

    fun rePopulateList() {
        //make sure list is clear
        questionList.clear()
        //populate addition question
        for (i in 1..5) {
            val operand1: Int = Random.nextInt(1, 99)
            val operand2: Int = Random.nextInt(1, 20)
            val answer = operand1 + operand2
            questionList.add(Question(i,operand1,operand2,1,answer))
        }
        //populate subtraction question
        for (i in 6..10){
            val operand1: Int = Random.nextInt(1, 99)
            val operand2: Int = Random.nextInt(1, 20)
            val answer = operand1 - operand2
            questionList.add(Question(i,operand1,operand2,2,answer))
        }
        //shuffle the question list
        questionList.shuffle()
    }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionList.size
    }
}