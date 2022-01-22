package com.example.android.quiz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val sharedPreferencesTexts = sharedPreferences.all.map {it.value}


        val resultList = findViewById<ListView>(R.id.score_list)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sharedPreferencesTexts)
        resultList.adapter = adapter


        val btnBack = findViewById<Button>(R.id.btn_back)
        val btnClear = findViewById<Button>(R.id.btn_clear)

        btnBack.setOnClickListener {
            val questionList = intent.getSerializableExtra("allQuestions")
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("allQuestions", questionList)
            startActivity(intent)
            finish()
        }

        btnClear.setOnClickListener {
            val sharedPreferencesBtnClick: SharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
            sharedPreferencesBtnClick.edit().clear().commit()
            startActivity(Intent(this, SplashScreenActivity::class.java))
            finish()
        }

    }


    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}