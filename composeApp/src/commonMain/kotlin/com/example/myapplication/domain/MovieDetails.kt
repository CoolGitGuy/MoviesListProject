package com.example.myapplication.domain

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails(
    val id: String,
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
    val posterUrl: String? = null,
    val backdropUrl: String? = null,
    val homepage: String? = null,
    val genres: List<String> = emptyList(),
    val collectionName: String? = null,
    val collectionPosterUrl: String? = null,
    val collectionBackdropUrl: String? = null,
    val trailerUrl: String? = null
)