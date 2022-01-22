package com.example.android.quiz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/*
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val splashIcon = findViewById<ImageView>(R.id.splash_trophy)
        splashIcon.alpha = 0f

        //splashIcon.animate().setDuration(1000).alpha(1f)

        splashIcon.animate().setDuration(1000).alpha(1f).withEndAction{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }

}
 */



class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val splashIcon = findViewById<ImageView>(R.id.splash_trophy)
        splashIcon.alpha = 0f

        splashIcon.animate().setDuration(1000).alpha(1f)
        getMyData()

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

                val allQuestions = makeDataUsable(responseBody)

                /*
                val myStringBuilder = StringBuilder()
                for(myData in responseBody) {
                    myStringBuilder.append(myData.name)
                    myStringBuilder.append("\n")
                }

                 */


                val splashIcon = findViewById<ImageView>(R.id.splash_trophy)

                splashIcon.animate().setDuration(1000).alpha(1f).withEndAction{


                    val intent = Intent(applicationContext, MainActivity::class.java)
                    val gson = Gson()
                    val questionList = gson.toJson(responseBody)



                    intent.putExtra("allQuestions", questionList);
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }

                //Toast.makeText(applicationContext, "dataLoaded", Toast.LENGTH_SHORT).show()

                //val testText = findViewById<TextView>(R.id.textView)
               // testText.text = myStringBuilder
            }

            override fun onFailure(call: Call<List<CountriesItem>?>, t: Throwable) {
                Toast.makeText(applicationContext, "Data can not be loaded", Toast.LENGTH_LONG).show()
                Log.d("SplashScreenActivity", "onFailure" + t.message)
            }
        })
    }

    private fun makeDataUsable(responseBody: List<CountriesItem>): ArrayList<Question> {
        val questionList = ArrayList<Question>()
        val alreadyUsedQuestion = ArrayList<Int?>()
        val test = responseBody.size
        // make questions
        for (i in 1..NUMBER_OF_QUESTIONS) {
            val correctAnswerPosition = generateRandom(0, 4, ArrayList<Int?>())
            val correctAnswer = generateRandom(0, responseBody.size - 2, alreadyUsedQuestion)
            alreadyUsedQuestion.add(correctAnswer)


            val allAnswersInQuestion = ArrayList<Int>()

            val alreadyUsedAnswersInOneQuestion = ArrayList<Int?>()
            alreadyUsedAnswersInOneQuestion.add(correctAnswerPosition)

            for (k in 1..4) {
                val randomAnswer = generateRandom(0, responseBody.size - 2, alreadyUsedAnswersInOneQuestion)
                if (k == correctAnswerPosition) {
                    allAnswersInQuestion.add(correctAnswer)
                } else {
                    allAnswersInQuestion.add(randomAnswer)
                    alreadyUsedAnswersInOneQuestion.add(randomAnswer)
                }
            }


            val question = Question(
                i,
                "Country ?",
                responseBody[correctAnswer].flags.png,
                responseBody[allAnswersInQuestion[0]].name,
                responseBody[allAnswersInQuestion[1]].name,
                responseBody[allAnswersInQuestion[2]].name,
                responseBody[allAnswersInQuestion[3]].name,
                correctAnswerPosition
            )
            questionList.add(question)
        }

        return questionList;
    }

    fun generateRandom(start: Int, end: Int, excludeRows: ArrayList<Int?>): Int {
        val rand = Random()
        val range = end - start + 1
        var random: Int = rand.nextInt(range) + 1
        while (excludeRows.contains(random)) {
            random = rand.nextInt(range) + 1
        }
        return random
    }

}