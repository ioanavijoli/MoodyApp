package com.example.moody.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Song(
    @Json(name = "title")
    val title: String,
    @Json(name = "year")
    val year: Int,
    @Json(name = "artist")
    val artist: String,
    @Json(name = "genre")
    val genre: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "id")
    val id: Int
)