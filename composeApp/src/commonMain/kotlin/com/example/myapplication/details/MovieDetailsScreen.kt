package com.example.myapplication.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
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

    MovieDetailsScreen(
        state = state,
        movieId = movieId,
        onClose = onClose,
        onPlayClick = { /* TODO */ }
    )
}

@Composable
private fun MovieDetailsScreen(
    state: MovieDetailsContract.UiState,
    movieId: String,
    onClose: () -> Unit,
    onPlayClick: () -> Unit
){
    Column {
        Button(onClick = onClose,modifier = Modifier.padding(vertical = 8.dp)) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        TitleScreen(onPlayClick)
        Overview()
        Info()
    }
}

@Composable
private fun Info() {
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
                InfoCard(title = "Budget", value = "$25M")
            }
            item {
                InfoCard(title = "Revenue", value = "$28M")
            }
            item {
                InfoCard(title = "Language", value = "EN")
            }
            item {
                InfoCard(title = "Popularity", value = "42.2")
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
        color = Color(0xFF26233A)
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
                color = Color(0xFFB8B5C8)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
    }
}

@Composable
private fun Overview() {
    Column {
        Text(
            text = "Overview", style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Text(
            text = "Imprisoned in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison...",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun TitleScreen(onPlayClick: () -> Unit) {
    Box {
        PosterWithPlayButton(
            posterUrl = "https://image.tmdb.org/t/p/w500/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
            backdropUrl = "https://image.tmdb.org/t/p/w500/zfbjgQE1uSd9wiPTX4VzsLi0rGG.jpg",
            onPlayClick = onPlayClick
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 152.dp, end = 16.dp, bottom = 24.dp)
        ) {
            Text(
                text = "The Shawshank Redemption",
                style = MaterialTheme.typography.headlineMedium.copy(
                    drawStyle = Stroke(4f)
                )
            )
            Text(
                text = "1994 - 142 min",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "2.3M votes",
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
                    containerColor = Color.Red,
                    contentColor = Color.White
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