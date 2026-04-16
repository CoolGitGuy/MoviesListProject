package com.example.myapplication

import androidx.compose.runtime.Composable
import com.example.myapplication.di.initKoin

@Composable
fun MoviesApp() {
    initKoin()
    MoviesNavigation("movies")
}