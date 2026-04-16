package com.example.myapplication.domain

import com.example.myapplication.list.SortOption
import com.example.myapplication.networking.model.MovieDetailApiModel

interface MovieRepository {
    suspend fun getMovies(sort: SortOption): List<Movie>
    suspend fun getMovieDetails(id: String): MovieDetails?
    suspend fun getMovieTrailer(id: String): String
}
