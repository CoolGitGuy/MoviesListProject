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

@Serializable
data class MovieDetailApiModel(
    val imdbId: String,
    val tmdbId: Int? = null,
    val title: String,
    val originalTitle: String? = null,
    val overview: String? = null,
    val tagline: String? = null,
    val releaseDate: String? = null,
    val year: Int? = null,
    val runtime: Int? = null,
    val budget: Long? = null,
    val revenue: Long? = null,
    val languageCode: String? = null,
    val popularity: Float? = null,
    val imdbRating: Float? = null,
    val imdbVotes: Int? = null,
    val tmdbRating: Float? = null,
    val tmdbVotes: Int? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val homepage: String? = null,
    val genres: List<GenreApiModel> = emptyList(),
    val collection: MovieCollection? = null
)

@Serializable
data class MovieCollection(
    val id: Int,
    val name: String,
    val posterPath: String? = null,
    val backdropPath: String? = null
)
