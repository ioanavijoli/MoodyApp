package com.example.moody

import com.example.moody.services.MovieService
import com.example.moody.services.SongService
import com.example.moody.services.UserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object HttpClient {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://6398b1f6fe03352a94db8aaa.mockapi.io/api/v1/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    val userService: UserService = retrofit.create(UserService::class.java)
    val movieService: MovieService = retrofit.create(MovieService::class.java)
    val songService: SongService = retrofit.create(SongService::class.java)

}