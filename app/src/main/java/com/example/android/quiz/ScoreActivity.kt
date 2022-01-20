package com.example.android.quiz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        /*
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val savedString: String? = sharedPreferences.getString("STRING_KEY", null)
        val textMsg = findViewById<TextView>(R.id.tv_text)
        textMsg.text = savedString
        */

        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val sharedPreferenceIds = sharedPreferences.all.map { it.key }
        val sharedPreferencesTexts = sharedPreferences.all.map {it.value}

        val resultList = findViewById<ListView>(R.id.score_list);

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sharedPreferencesTexts)
        resultList.adapter = adapter

    }


    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}