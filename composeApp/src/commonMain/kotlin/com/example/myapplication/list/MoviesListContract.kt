package com.example.myapplication.list

import com.example.myapplication.domain.Movie


enum class SortOption{
    RatingDESC,YearDESC,TitleDESC,PopularityDESC,RatingASC,YearASC,TitleASC,PopularityASC
}
interface MoviesListContract {

    data class UiState(
        val movies: List<Movie> = emptyList(),
        val isLoading: Boolean = false,
        val selectedSort: SortOption = SortOption.RatingDESC,
        val error: Throwable? = null
    )

    sealed interface MoviesListIntent {
        data class SortChanged(val sort: SortOption) : MoviesListIntent
        data class MovieClicked(val movieId: String) : MoviesListIntent
        data object RetryClicked : MoviesListIntent
        data object LoadMovies : MoviesListIntent
    }

}