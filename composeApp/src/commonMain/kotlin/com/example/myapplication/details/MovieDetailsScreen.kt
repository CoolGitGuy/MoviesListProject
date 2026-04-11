package com.example.myapplication.details

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MovieDetailsScreen(movieId: String) {
    Scaffold { Text(movieId) }
}