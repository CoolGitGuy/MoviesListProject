package com.example.myapplication.details

import com.example.myapplication.domain.Movie
import com.example.myapplication.domain.MovieDetails

interface MovieDetailsContract {
    data class UiState(
        val movie: MovieDetails? = null,
        val movieUrl : String? = null,
        val isLoading: Boolean = true,
        val error: Throwable? = null
    )
}