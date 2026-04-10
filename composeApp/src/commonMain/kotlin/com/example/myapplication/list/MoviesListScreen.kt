package com.example.myapplication.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.Movie
import myapplication.composeapp.generated.resources.Res
import myapplication.composeapp.generated.resources.compose_multiplatform
import myapplication.composeapp.generated.resources.logoimdb
import myapplication.composeapp.generated.resources.movienotfound
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun MoviesListScreen() {
    MoviesListScreen(
        state = MoviesListContract.UiState(movies = listOf(
            Movie(1, "The Shawshank Redemption", 1994, "Drama", 9.3, 2345678,""),
            Movie(2, "The Godfather", 1972, "Crime", 9.2, 1623456,""),
            Movie(3, "The Dark Knight", 2008, "Action", 9.0, 2345678,""),
            Movie(4, "Pulp Fiction", 1994, "Crime", 8.9, 1789456,""),
            Movie(5, "Forrest Gump", 1994, "Drama", 8.8, 1893456,""),
        ))

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MoviesListScreen(
    state: MoviesListContract.UiState
) {
    Scaffold(
        topBar = {
            Column {
                TopBarPremier()
                SortChipMenu(
                    selected = SortOption.Rating,
                    onOptionSelected = {  }
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
        else ScreenContent(modifier = Modifier.padding(innerPadding), state = state)

    }
}



@ExperimentalMaterial3Api
@Composable
fun TopBarPremier() {
    TopAppBar(
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
fun ScreenContent(modifier: Modifier = Modifier,state: MoviesListContract.UiState) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Text("100 movies")
        state.movies.forEach { movie ->
            MovieListItem(
                headline = movie.title,
                overline = movie.releaseYear.toString(),
                supporting = movie.genre,
                trailing1 = movie.rating.toString(),
                trailing2 = "${movie.votes} votes"
            )
        }

    }
}

@Composable
fun MovieListItem(image: DrawableResource = Res.drawable.compose_multiplatform, headline : String = "headline", overline : String = "overline", supporting : String = "supporting", trailing1 : String = "trailing", trailing2 : String = "trailing2") {
    Surface(modifier = Modifier.padding(2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        ListItem(
            modifier = Modifier.fillMaxWidth().clickable(onClick = { /* klik na list item */ }),
            headlineContent = { Text(headline) },
            overlineContent = { Text(overline) },
            supportingContent = { Text(supporting) },
            leadingContent = {
                Image(
                    painter = painterResource(image),
                    contentDescription = "Poster filma",
                    modifier = Modifier.size(64.dp)
                )
            },
            trailingContent = {
                Column {
                    Text("⭐$trailing1", color = Color.Yellow)
                    Text("$trailing2")
                }
            },
        )
    }
}