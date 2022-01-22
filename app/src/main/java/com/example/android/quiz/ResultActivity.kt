package com.example.android.quiz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ResultActivity : AppCompatActivity() {

    private val sharedPrefFile = "com.example.quiz.score"

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

        saveData()

        finishBtn.setOnClickListener {
            val questionList = intent.getSerializableExtra("allQuestions")
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("allQuestions", questionList);
            startActivity(intent)
            finish()
        }
    }


    override fun onBackPressed() {
        val questionList = intent.getSerializableExtra("allQuestions")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        intent.putExtra("allQuestions", questionList);
        finish()
    }

    private fun saveData() {
        val userName = intent.getStringExtra(Constants.USER_NAME)
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)

        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply{
            putString("resultFor_$userName", "$userName score is $correctAnswers out of $totalQuestions")
        }.apply()

        Toast.makeText(this, "data saved", Toast.LENGTH_SHORT).show()
    }

}