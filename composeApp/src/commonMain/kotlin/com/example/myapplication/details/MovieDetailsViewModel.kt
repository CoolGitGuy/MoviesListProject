package com.example.myapplication.details

import androidx.lifecycle.ViewModel
import com.example.myapplication.list.MoviesListContract
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieDetailsViewModel : ViewModel() {
    private val _state = MutableStateFlow(MovieDetailsContract.UiState())
    val state = _state.asStateFlow()

}