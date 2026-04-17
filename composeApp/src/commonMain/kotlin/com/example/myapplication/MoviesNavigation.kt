package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.savedstate.read
import com.example.myapplication.details.MovieDetailsScreen
import com.example.myapplication.details.MovieDetailsViewModel
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
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.read { getStringOrNull(MOVIE_ID) } ?: return@composable // Nisam znao kako ovo da izvucem :(
            val viewModel = koinViewModel<MovieDetailsViewModel>()

            MovieDetailsScreen(
                viewModel = viewModel,
                movieId = movieId,
                onClose = { navController.navigateUp() }
            )
        }
    }
}

private fun NavController.navigateToMovieDetails(movieId : String) {
    navigate("movies/$movieId")
}

const val MOVIE_ID = "movieId"