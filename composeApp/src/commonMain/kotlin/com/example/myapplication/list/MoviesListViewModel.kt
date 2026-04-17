package com.example.myapplication.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesListViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _state = MutableStateFlow(MoviesListContract.UiState())
    val state = _state.asStateFlow()

    init {
        loadMovies(_state.value.selectedSort)
    }

    private fun loadMovies(sort: SortOption) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _state.value = _state.value.copy(isLoading = true, error = null)

                try {
                    val currentState = _state.value
                    val movies = movieRepository.getMovies(
                        sort = sort,
                        query = currentState.searchQuery.ifBlank { null },
                        minYear = currentState.yearFrom.toIntOrNull(),
                        maxYear = currentState.yearTo.toIntOrNull(),
                        minRating = currentState.minRating.takeIf { it > 0f },
                        genre = currentState.selectedGenre
                    )

                    _state.value = _state.value.copy(
                        movies = movies,
                        isLoading = false,
                        error = null
                    )
                } catch (error: Throwable) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = error
                    )
                }
            }
        }
    }

    private fun loadGenres(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val genres = movieRepository.getGenres()

                    _state.value = _state.value.copy(
                        genres = genres,
                        error = null
                    )
                } catch (error: Throwable) {
                    _state.value = _state.value.copy(
                        error = error
                    )
                }
            }
        }
    }

    fun onIntent(intent: MoviesListContract.MoviesListIntent) {
        when (intent) {

            is MoviesListContract.MoviesListIntent.SortChanged -> {
                _state.value = _state.value.copy(selectedSort = intent.sort)
                loadMovies(intent.sort)
            }
            MoviesListContract.MoviesListIntent.LoadMovies -> {
                loadMovies(_state.value.selectedSort)
            }
            MoviesListContract.MoviesListIntent.RetryClicked -> {
                loadMovies(_state.value.selectedSort)
            }
            MoviesListContract.MoviesListIntent.OpenFilter -> {
                _state.value = _state.value.copy(isFilterOpen = true)
                loadGenres()
            }

            MoviesListContract.MoviesListIntent.CloseFilter -> {
                _state.value = _state.value.copy(isFilterOpen = false)
            }
            is MoviesListContract.MoviesListIntent.SearchQueryChanged -> {
                _state.value = _state.value.copy(searchQuery = intent.value)
            }
            is MoviesListContract.MoviesListIntent.GenreChanged -> {
                _state.value = _state.value.copy(selectedGenre = intent.genre)
            }
            is MoviesListContract.MoviesListIntent.YearFromChanged -> {
                _state.value = _state.value.copy(yearFrom = intent.value)
            }
            is MoviesListContract.MoviesListIntent.YearToChanged -> {
                _state.value = _state.value.copy(yearTo = intent.value)
            }
            is MoviesListContract.MoviesListIntent.MinRatingChanged -> {
                _state.value = _state.value.copy(minRating = intent.value)
            }
            MoviesListContract.MoviesListIntent.ClearFilters -> {
                _state.value = _state.value.copy(
                    searchQuery = "",
                    selectedGenre = null,
                    yearFrom = "1920",
                    yearTo = "2025",
                    minRating = 0f
                )
            }
            MoviesListContract.MoviesListIntent.ApplyFilters -> {
                _state.value = _state.value.copy(isFilterOpen = false)
                loadMovies(_state.value.selectedSort)
            }
            is MoviesListContract.MoviesListIntent.MovieClicked -> Unit
        }
    }



}
