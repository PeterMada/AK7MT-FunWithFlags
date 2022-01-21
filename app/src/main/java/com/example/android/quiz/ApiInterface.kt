package com.example.android.quiz

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("regionalbloc/eu")
    fun getData(): Call<List<CountriesItem>>
}