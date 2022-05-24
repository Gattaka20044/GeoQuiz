package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders


const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"
private const val EXTRA_INDEX = "com.bignerdranch.android.geoquiz.index"
private const val KEY_CHEATER = "cheater"


class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button

    private var answerIsTrue = false

    var cheaterMap =  mutableMapOf<Int,Boolean>()


    private val cheatViewModel: CheatViewModel by lazy {
        ViewModelProviders.of(this).get(CheatViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        val cheaterStatus = savedInstanceState?.getBoolean(KEY_CHEATER, false) ?: false
        cheatViewModel.cheaterStatus = cheaterStatus
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)

        showAnswerButton.setOnClickListener {
            val index = intent.getIntExtra(EXTRA_INDEX, -1)
            cheaterMap += 1 to  true
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            cheatViewModel.cheaterStatus()
            answerTextView.setText(answerText)
            setAnswerShownResult(true)

        }
        if (cheatViewModel.cheaterStatus){
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }

    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)

        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean, index: Int): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                putExtra(EXTRA_INDEX, index)
            }
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putBoolean(KEY_CHEATER, cheatViewModel.cheaterStatus)
    }
}