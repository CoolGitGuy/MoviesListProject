package com.example.myapplication.list

import com.example.myapplication.domain.Movie


enum class SortOption{
    Rating,Year,Title,Popularity
}
interface MoviesListContract {

    data class UiState(
        val movies: List<Movie> = emptyList(),
        val isLoading: Boolean = false,
        val selectedSort: SortOption = SortOption.Rating,
        val error: Throwable? = null
    )

    sealed interface MoviesListIntent {
        data class SortChanged(val sort: SortOption) : MoviesListIntent
        data class MovieClicked(val movieId: String) : MoviesListIntent
        data object RetryClicked : MoviesListIntent
        data object LoadMovies : MoviesListIntent
    }

}