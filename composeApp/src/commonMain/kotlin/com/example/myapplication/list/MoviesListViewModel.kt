package com.example.myapplication.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
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
            withContext(Dispatchers.IO){
                _state.value = _state.value.copy(isLoading = true, error = null)

                try {
                    val movies = movieRepository.getMovies(sort)

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
            is MoviesListContract.MoviesListIntent.MovieClicked -> Unit
        }
    }



}