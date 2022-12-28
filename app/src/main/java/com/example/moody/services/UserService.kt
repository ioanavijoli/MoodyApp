package com.example.moody.services


import com.example.moody.data.User
import com.example.moody.data.UserDetails
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @POST("users")
    fun createUser(@Body user: User): Call<UserDetails>

    @GET("users")
    fun getUserByEmail(@Query(value="email", encoded=true) email : String) : Call <List<UserDetails>>

    @POST("login")
    fun logInUser(@Body logInUser: UserDetails): Call<UserDetails>

    @GET("login")
    fun getLoggedInUser(@Query(value="id", encoded=true) id : Int) : Call <List<UserDetails>>

    @DELETE("login/{id}")
    fun logOutUser(@Path("id") userId: Int): Call<UserDetails>

}