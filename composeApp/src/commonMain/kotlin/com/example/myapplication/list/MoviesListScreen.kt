package com.example.myapplication.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.myapplication.domain.Movie
import io.github.aakira.napier.Napier
import myapplication.composeapp.generated.resources.Res
import myapplication.composeapp.generated.resources.compose_multiplatform
import myapplication.composeapp.generated.resources.logoimdb
import myapplication.composeapp.generated.resources.movienotfound
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun MoviesListScreen(
    viewModel: MoviesListViewModel,
    onMovieClick: (movieId: String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    MoviesListScreen(
        state = state,
        onMovieClick = onMovieClick,
        onSortSelected = {
            viewModel.onIntent(
                MoviesListContract.MoviesListIntent.SortChanged(it)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MoviesListScreen(
    state: MoviesListContract.UiState,
    onMovieClick: (movieId: String) -> Unit,
    onSortSelected: (SortOption) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column {
                TopBarPremier()
                SortChipMenu(
                    selected = state.selectedSort,
                    onOptionSelected = onSortSelected
                )
            }
        }
    ) {innerPadding ->
        if(state.isLoading){
            Column(modifier = Modifier.fillMaxWidth().padding(innerPadding).padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Text("Loading movies...", modifier = Modifier.padding(start = 8.dp))
            }
        } else if(state.error != null){
            Column(modifier = Modifier.fillMaxWidth().padding(innerPadding).padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Info icon",
                    modifier = Modifier.size(48.dp),
                    tint = Color.Red

                )
                Text("Error: ${state.error.message}", modifier = Modifier.padding(start = 8.dp))
            }
        } else if(state.movies.isEmpty()){
            Column(modifier = Modifier.fillMaxWidth().padding(innerPadding).padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(Res.drawable.movienotfound),
                    contentDescription = "Info icon",
                    modifier = Modifier.size(48.dp),
                )
                Text("No Movies Found", modifier = Modifier.padding(start = 8.dp))
            }
        }
        else ScreenContent(modifier = Modifier.padding(innerPadding), state = state, onMovieClick = onMovieClick)

    }
}



@ExperimentalMaterial3Api
@Composable
fun TopBarPremier() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF111111),
            titleContentColor = Color(0xFFF5C518),
            actionIconContentColor = Color(0xFFF5C518)
        ),
        title = {
            Image(
                painter = painterResource(Res.drawable.logoimdb),
                modifier = Modifier.height(36.dp),
                contentDescription = "Ikona"
            )
        },
        actions = {
            IconButton(onClick = { /* klik na dugme */ }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Ikona"
                )
            }
        },
    )
}

@Composable
fun SortChipMenu(
    selected: SortOption,
    onOptionSelected: (SortOption) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        SuggestionChip(
            onClick = { expanded = true },
            label = { Text("Sort: ${selected.name}") },
            icon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            SortOption.entries.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.name) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier,state: MoviesListContract.UiState,onMovieClick: (movieId: String) -> Unit = { }) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Text("${state.movies.get(0).totalItems} movies")
        state.movies.forEach { movie ->
            MovieListItem(
                movie = movie,
                onClick = { onMovieClick(movie.id) }
            )
        }

    }
}

@Composable
fun MovieListItem(
                  movie: Movie,
                  onClick : () -> Unit) {
    Surface(
        modifier = Modifier.padding(2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surface,
                headlineColor = MaterialTheme.colorScheme.onSurface,
                supportingColor = MaterialTheme.colorScheme.onSurfaceVariant,
                overlineColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
            headlineContent = { Text(movie.title) },
            overlineContent = { Text(movie.releaseYear.toString()) },
            supportingContent = { Text(movie.genres.toString()) },
            leadingContent = {
                AsyncImage(
                    model = movie.posterImageUrl(),
                    contentDescription = "Poster filma",
                    modifier = Modifier.size(84.dp),
                    error = painterResource(Res.drawable.compose_multiplatform),
                    fallback = painterResource(Res.drawable.compose_multiplatform)
                )

            },
            trailingContent = {
                Column {
                    Text("⭐${movie.rating.toString()}", color = MaterialTheme.colorScheme.primary)
                    Text("${movie.votes} votes")
                }
            },
        )
    }
}


private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"
private fun Movie.posterImageUrl(): String? {
    return posterUrl?.let { "$POSTER_BASE_URL$it" }
}