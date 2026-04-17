package com.example.myapplication.domain

import com.example.myapplication.list.SortOption
import com.example.myapplication.networking.model.MovieDetailApiModel

interface MovieRepository {
    suspend fun getMovies(
        sort: SortOption,
        query: String?,
        minYear: Int?,
        maxYear: Int?,
        minRating: Float?,
        genre: String? = null
    ): List<Movie>

    suspend fun getMovieDetails(id: String): MovieDetails?
    suspend fun getMovieTrailer(id: String): String
    suspend fun getMovieCast(id: String): List<Cast>
    suspend fun getGenres(): List<Genre>
    suspend fun searchImages(id: String): List<String>?
}
