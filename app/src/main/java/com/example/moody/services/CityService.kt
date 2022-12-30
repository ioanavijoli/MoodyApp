package com.example.moody.services

import com.example.moody.data.City
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CityService {
    @GET("cities")
    fun getCities(): Call<List<City>>

    @GET("cities")
    fun getCitiesByMood(@Query(value = "mood", encoded = true) mood: String): Call<List<City>>
}