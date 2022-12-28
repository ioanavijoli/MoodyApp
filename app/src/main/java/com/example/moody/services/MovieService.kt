package com.example.moody.services

import com.example.moody.data.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieService {

    @GET("movies")
    fun getMovies(): Call<List<Movie>>

    @GET("movies")
    fun getMoviesByGenre(@Query(value = "genres", encoded = true) genres: String): Call<List<Movie>>

}