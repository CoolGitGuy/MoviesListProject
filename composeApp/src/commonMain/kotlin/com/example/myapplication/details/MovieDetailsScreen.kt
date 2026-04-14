package com.example.myapplication.details

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MovieDetailsScreen(
    movieId: String,
    onClose: () -> Unit
) {
    Scaffold(
        topBar = {
            IconButton(onClick = onClose){
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    ){innerPadding ->
        Text(
            text = "Movie Details for Movie ID: $movieId",
            modifier = androidx.compose.ui.Modifier.padding(innerPadding)
        )
    }
}