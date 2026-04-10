package com.example.myapplication.list

import com.example.myapplication.domain.Movie

interface MoviesListContract {

    data class UiState(
        val movies: List<Movie> = emptyList(),
        val isLoading: Boolean = false,
        val error: Throwable? = null
    )
}