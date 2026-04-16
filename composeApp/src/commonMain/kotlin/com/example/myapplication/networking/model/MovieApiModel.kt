package com.example.myapplication.networking.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieListItem(val imdbId: String,
                         val title: String,
                         val year: Int? = null,
                         val imdbRating: Float? = null,
                         val imdbVotes: Int? = null,
                         val posterPath : String? = null,
                         val genres: List<GenreApiModel>? = emptyList())

@Serializable
data class GenreApiModel(
    val id: Int,
    val name: String
)