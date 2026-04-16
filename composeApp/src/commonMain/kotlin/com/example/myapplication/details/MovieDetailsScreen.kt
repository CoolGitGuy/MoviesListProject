package com.example.myapplication.details

import com.example.myapplication.openUrl
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import myapplication.composeapp.generated.resources.Res
import myapplication.composeapp.generated.resources.movienotfound
import org.jetbrains.compose.resources.painterResource

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel,
    movieId: String,
    onClose: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.loadMovieDetails(movieId)
    }

    MovieDetailsScreen(
        state = state,
        movieId = movieId,
        onClose = onClose,
        onPlayClick = {
            state.movieUrl?.let { key ->
                openUrl("https://www.youtube.com/watch?v=$key")
            }
        }
    )
}

@Composable
private fun MovieDetailsScreen(
    state: MovieDetailsContract.UiState,
    movieId: String,
    onClose: () -> Unit,
    onPlayClick: () -> Unit
){
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Button(onClick = onClose,modifier = Modifier.padding(vertical = 8.dp)) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
        if(state.isLoading){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Text("Loading movie details...", modifier = Modifier.padding(start = 8.dp))
            }
        }else if(state.error != null){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Info icon",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(64.dp)
                )
                Text("Error: ${state.error.message}", modifier = Modifier.padding(start = 8.dp))
            }
        } else if(state.movie == null){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(Res.drawable.movienotfound),
                    contentDescription = "Movie not found",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(64.dp)
                )
                Text("Movie not found", modifier = Modifier.padding(start = 8.dp))
            }
        } else{
            TitleScreen(
                onPlayClick = onPlayClick,
                posterPath = state.movie.posterUrl ?: "",
                backdropPath = state.movie.backdropUrl ?: "",
                title = state.movie.title ?: "Unknown Title",
                year = state.movie.year.toString() ?: "N/A",
                minutes = state.movie.runtime?.toString() ?: "N/A",
                votes = state.movie.imdbVotes?.formatCompact().toString() ?: "N/A"
            )
            Overview(state.movie.overview ?: "No overview available.")
            Info(
                budget = state.movie.budget?.formatCompact().toString() ?: "N/A",
                revenue = state.movie.revenue?.formatCompact().toString() ?: "N/A",
                language = state.movie.languageCode ?: "N/A",
                popularity = state.movie.popularity?.toString() ?: "N/A"
            )

            Column {
                Text(
                    text = "Images", style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                LazyRow(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.images?.forEach { imagePath ->
                        item {
                            AsyncImage(

                                model = "https://image.tmdb.org/t/p/w500$imagePath",
                                contentDescription = null,
                                modifier = Modifier
                                    .size(width = 200.dp, height = 120.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }

            Actors(state)
            Spacer(modifier = Modifier.height(50.dp)) // samo da vidimo glumce :D
        }
    }
}

@Composable
private fun Actors(state: MovieDetailsContract.UiState) {
    Column {
        Text(
            text = "Actors", style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.cast.forEach { actor ->
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(100.dp)
                    ) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w200${actor.profileUrl}",
                            contentDescription = actor.name,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(40.dp))
                                .border(1.dp, Color.Gray, RoundedCornerShape(40.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = actor.name ?: "Unknown",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(top = 4.dp),
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Info(
    budget: String,
    revenue: String,
    language: String,
    popularity: String
) {
    Column {
        Text(
            text = "Info", style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                InfoCard(title = "Budget", value = budget)
            }
            item {
                InfoCard(title = "Revenue", value = revenue)
            }
            item {
                InfoCard(title = "Language", value = language.uppercase())
            }
            item {
                InfoCard(title = "Popularity", value = popularity)
            }
        }
    }
}

@Composable
fun InfoCard(
    title: String,
    value: String
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier
                .width(92.dp)
                .padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun Overview(overview: String) {
    Column {
        Text(
            text = "Overview", style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Text(
            text = overview,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun TitleScreen(
    onPlayClick: () -> Unit,
    posterPath: String,
    backdropPath: String,
    title: String,
    year: String,
    minutes : String,
    votes: String
) {
    Box {
        PosterWithPlayButton(
            posterUrl = "https://image.tmdb.org/t/p/w500${posterPath}",
            backdropUrl = "https://image.tmdb.org/t/p/w500${backdropPath}",
            onPlayClick = onPlayClick
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 152.dp, end = 16.dp, bottom = 24.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    drawStyle = Stroke(4f)
                )
            )
            Text(
                text = "${year} - ${minutes} min",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "${votes} votes",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Composable
fun PosterWithPlayButton(
    posterUrl: String,
    backdropUrl: String,
    onPlayClick: () -> Unit
) {
    Box(
        modifier = Modifier
        .fillMaxWidth()
        .height(320.dp)
    ){
        Box {
            AsyncImage( // Thumbnail
                model = backdropUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = onPlayClick,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Icon(

                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play"
                )
            }
        }

        AsyncImage( // Poster
            model = posterUrl,
            contentDescription = null,
            modifier = Modifier
                .size(width = 120.dp, height = 180.dp)
                .align(Alignment.BottomStart)
                .padding(start = 16.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

fun Long?.formatCompact(): String {
    if (this == null) return "N/A"

    return when {
        this >= 1_000_000_000 -> compact(this / 1_000_000_000.0, "B")
        this >= 1_000_000 -> compact(this / 1_000_000.0, "M")
        this >= 1_000 -> compact(this / 1_000.0, "K")
        else -> this.toString()
    }
}

fun Int?.formatCompact(): String {
    return this?.toLong().formatCompact()
}

private fun compact(value: Double, suffix: String): String {
    val rounded = (value * 10).toInt() / 10.0
    val text = if (rounded % 1.0 == 0.0) {
        rounded.toInt().toString()
    } else {
        rounded.toString()
    }
    return text + suffix
}


