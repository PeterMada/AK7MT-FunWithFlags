package com.example.android.quiz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

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

        /*
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putInt("id_key",id)
        editor.putString("name_key",name)
        editor.apply()
        editor.commit()
*/

        finishBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}