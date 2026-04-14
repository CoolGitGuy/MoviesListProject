package com.example.myapplication.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MoviesListViewModel : ViewModel() {

    private val _state = MutableStateFlow(MoviesListContract.UiState())

    val state = _state.asStateFlow()
}