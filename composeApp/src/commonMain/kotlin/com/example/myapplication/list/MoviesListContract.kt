package com.example.myapplication.list

import com.example.myapplication.domain.Genre
import com.example.myapplication.domain.Movie


enum class SortOption{
    RatingDESC,YearDESC,TitleDESC,PopularityDESC,RatingASC,YearASC,TitleASC,PopularityASC
}
interface MoviesListContract {

    data class UiState(
        val movies: List<Movie> = emptyList(),
        val genres: List<Genre> = emptyList(),
        val isLoading: Boolean = false,
        val selectedSort: SortOption = SortOption.RatingDESC,
        val error: Throwable? = null,

        val isFilterOpen: Boolean = false,
        val searchQuery: String = "",
        val selectedGenre: String? = null,
        val yearFrom: String = "1920",
        val yearTo: String = "2025",
        val minRating: Float = 0f,
        )

    sealed interface MoviesListIntent {
        data class SortChanged(val sort: SortOption) : MoviesListIntent
        data class MovieClicked(val movieId: String) : MoviesListIntent
        data object RetryClicked : MoviesListIntent
        data object LoadMovies : MoviesListIntent


        data object OpenFilter : MoviesListIntent
        data object CloseFilter : MoviesListIntent
        data class SearchQueryChanged(val value: String) : MoviesListIntent
        data class GenreChanged(val genre: String?) : MoviesListIntent
        data class YearFromChanged(val value: String) : MoviesListIntent
        data class YearToChanged(val value: String) : MoviesListIntent
        data class MinRatingChanged(val value: Float) : MoviesListIntent
        data object ClearFilters : MoviesListIntent
        data object ApplyFilters : MoviesListIntent

    }

}
