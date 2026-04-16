package com.example.myapplication.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.MovieRepository
import com.example.myapplication.list.MoviesListContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val _state = MutableStateFlow(MovieDetailsContract.UiState())
    val state = _state.asStateFlow()


    fun loadMovieDetails(movieId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                coroutineScope {
                    val movieDeferred = async(Dispatchers.IO) { movieRepository.getMovieDetails(movieId) }
                    val trailerDeferred = async(Dispatchers.IO) { movieRepository.getMovieTrailer(movieId) }
                    val castDeferred = async(Dispatchers.IO) { movieRepository.getMovieCast(movieId) }
                    val imagesDeferred = async(Dispatchers.IO) { movieRepository.searchImages(movieId) }

                    _state.value = _state.value.copy(
                        movie = movieDeferred.await(),
                        movieUrl = trailerDeferred.await(),
                        cast = castDeferred.await(),
                        images = imagesDeferred.await() ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
            } catch (t: Throwable) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = t
                )
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

    suspend fun loadMovieCast(movieId: String) {
        _state.value = _state.value.copy(isLoading = true, error = null)

        try {
            val cast = movieRepository.getMovieCast(movieId)

            _state.value = _state.value.copy(
                cast = cast,
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

    suspend fun searchImages(movieId: String) {
        _state.value = _state.value.copy(isLoading = true, error = null)

        try {
            val images = movieRepository.searchImages(movieId)

            _state.value = _state.value.copy(
                images = images,
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