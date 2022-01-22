package com.example.android.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson

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
                val splashIcon = findViewById<ImageView>(R.id.splash_trophy)

                splashIcon.animate().setDuration(1000).alpha(1f).withEndAction{
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    val gson = Gson()
                    val questionList = gson.toJson(responseBody)

                    intent.putExtra("allQuestions", questionList)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }

            }

            override fun onFailure(call: Call<List<CountriesItem>?>, t: Throwable) {
                Toast.makeText(applicationContext, "Data can not be loaded", Toast.LENGTH_LONG).show()
                Log.d("SplashScreenActivity", "onFailure" + t.message)
            }
        })
    }
}