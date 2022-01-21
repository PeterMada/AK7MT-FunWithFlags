package com.example.android.quiz

import android.animation.AnimatorSet
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import kotlin.system.exitProcess
import android.content.DialogInterface
import android.os.Process
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

const val BASE_URL = "https://restcountries.com/v2/"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        getMyData()


        val btnStart = findViewById<Button>(R.id.btn_start)
        val inputText = findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.et_text)

        btnStart.setOnClickListener {
            if(inputText.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra(Constants.USER_NAME, inputText.text.toString())
                startActivity(intent)
                finish()
            }
        }

        /*
        supportActionBar?.hide()
        Handler().postDelayed({
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

         */
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<CountriesItem>?> {
            override fun onResponse(
                call: Call<List<CountriesItem>?>,
                response: Response<List<CountriesItem>?>
            ) {
                val responseBody = response.body()!!

                val myStringBuilder = StringBuilder()
                for(myData in responseBody) {
                    myStringBuilder.append(myData.name)
                    myStringBuilder.append("\n")
                }

                val testText = findViewById<TextView>(R.id.textView)
                testText.text = myStringBuilder
            }

            override fun onFailure(call: Call<List<CountriesItem>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure" + t.message)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.score -> {
                startActivity(Intent(this, ScoreActivity::class.java))
                finish()
                true
            }
            R.id.about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}