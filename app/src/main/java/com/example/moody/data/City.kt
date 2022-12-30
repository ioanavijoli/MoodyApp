package com.example.moody.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "country")
    val country: String,
    @Json(name = "inhabitants")
    val inhabitants: Int,
    @Json(name = "tourist_attractions")
    val tourist_attractions: List<String>,
    @Json(name = "restaurants")
    val restaurants: List<String>,
    @Json(name = "mood")
    val mood: String,
    @Json(name = "image")
    val image: String,
)