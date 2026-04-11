package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.details.MovieDetailsScreen
import com.example.myapplication.list.MoviesListScreen

@Composable
fun MoviesNavigation(startDestination: String,) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = "movies") {
            MoviesListScreen(onMovieClick = { navController.navigate("movies/${it.toString()}") })

        }

        composable(route = "movies/{id}") {
            MovieDetailsScreen(movieId = 1.toString())
        }
    }
}