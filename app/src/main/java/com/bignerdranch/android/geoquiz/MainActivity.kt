package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log


import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {



    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    private var questionResultCorrect: Int = 0
    private var flagOtvet: Boolean = false

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_ocean, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)

    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "OnCreate")

        setContentView(R.layout.activity_main)

        val provider: ViewModelProvider = ViewModelProviders.of(this)
        val quizViewModel = provider.get(QuizViewModel::class.java)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)



        trueButton.setOnClickListener{
            if (!flagOtvet){
                checkAnswer(true)
                flagOtvet = true
            }
        }

        falseButton.setOnClickListener{
            if (!flagOtvet){
                checkAnswer(false)
                flagOtvet = true
            }
        }

        nextButton.setOnClickListener{
            flagOtvet = false
            if((currentIndex + 1) == questionBank.size){
                Log.d(TAG, "Yes")
                questionResult(questionResultCorrect, questionBank.size)
                questionResultCorrect = 0
            }
            currentIndex = (currentIndex + 1) % questionBank.size

            updateQuestion()
        }

        updateQuestion()
    }

    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId :Int
        if (userAnswer == correctAnswer){
           messageResId = R.string.correct_toast
           questionResultCorrect ++
        }else{
            messageResId = R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun questionResult(correct: Int, size: Int){
        val questionResult = (correct / size.toDouble()) * 100
        val message = "Correct answers ${"%.2f".format(questionResult)}"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


}
