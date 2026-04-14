package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.details.MovieDetailsScreen
import com.example.myapplication.list.MoviesListScreen
import com.example.myapplication.list.MoviesListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MoviesNavigation(startDestination: String,) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(route = "movies") {
            val viewModel = koinViewModel<MoviesListViewModel>()

            MoviesListScreen(
                onMovieClick = { navController.navigateToMovieDetails(movieId = it) },
                viewModel = viewModel
            )

        }

        composable(
            route = "movies/{$MOVIE_ID}",
            arguments = listOf(
                navArgument(MOVIE_ID) {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) {
            MovieDetailsScreen(
                movieId = 1.toString(),
                onClose = { navController.navigateUp() }
            )
        }
    }
}

private fun NavController.navigateToMovieDetails(movieId : Int) {
    navigate("movies/$movieId")
}

const val MOVIE_ID = "movieId"