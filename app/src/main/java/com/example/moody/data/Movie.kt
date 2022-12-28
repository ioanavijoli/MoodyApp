package com.example.moody.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "year")
    val year: Int,
    @Json(name = "runtime")
    val runtime: Int,
    @Json(name = "genres")
    val genres: List<String>,
    @Json(name = "director")
    val director: String,
    @Json(name = "actors")
    val actors: String,
    @Json(name = "plot")
    val plot: String,
    @Json(name = "posterUrl")
    val posterUrl: String
)