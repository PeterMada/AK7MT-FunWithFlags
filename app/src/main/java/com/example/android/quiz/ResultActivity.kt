package com.example.android.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val usernameEl = findViewById<TextView>(R.id.result_name)
        val resultScoreEl = findViewById<TextView>(R.id.result_score)
        val finishBtn = findViewById<Button>(R.id.result_finish)

        val userName = intent.getStringExtra(Constants.USER_NAME)
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        usernameEl.text = userName
        resultScoreEl.text = "Your score is $correctAnswers out of $totalQuestions"


        finishBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}