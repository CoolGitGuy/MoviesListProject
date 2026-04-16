package com.example.myapplication.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.MovieRepository
import com.example.myapplication.list.MoviesListContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val _state = MutableStateFlow(MovieDetailsContract.UiState())
    val state = _state.asStateFlow()


    fun loadMovieDetails(movieId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loadMovieTrailer(movieId)
                _state.value = _state.value.copy(isLoading = true, error = null)

                try {
                    val movieDetails = movieRepository.getMovieDetails(movieId)

                    _state.value = _state.value.copy(
                        movie = movieDetails,
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

    suspend fun loadMovieTrailer(movieId: String) {
                _state.value = _state.value.copy(isLoading = true, error = null)

                try {
                    val trailerUrl = movieRepository.getMovieTrailer(movieId)

                    _state.value = _state.value.copy(
                        movieUrl = trailerUrl,
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