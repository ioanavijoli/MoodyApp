package com.example.moody.services

import com.example.moody.data.Song
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface SongService {

    @GET("songs")
    fun getSongs(): Call<List<Song>>

    @GET("songs")
    fun getSongsByGenre(@Query(value = "genre", encoded = true) genre: String): Call<List<Song>>

}