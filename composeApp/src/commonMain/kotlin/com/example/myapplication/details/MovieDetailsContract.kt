package com.example.myapplication.details

import com.example.myapplication.domain.Movie

interface MovieDetailsContract {
    data class UiState(
        val movie: Movie? = null,
        val movieDescription : String? = null,
        val isLoading: Boolean = false,
        val error: Throwable? = null
    )
}