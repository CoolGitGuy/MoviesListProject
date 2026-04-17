package com.example.myapplication.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.myapplication.domain.Genre
import com.example.myapplication.domain.Movie
import myapplication.composeapp.generated.resources.Res
import myapplication.composeapp.generated.resources.compose_multiplatform
import myapplication.composeapp.generated.resources.logoimdb
import myapplication.composeapp.generated.resources.movienotfound
import org.jetbrains.compose.resources.painterResource

@Composable
fun MoviesListScreen(
    viewModel: MoviesListViewModel,
    onMovieClick: (movieId: String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    if (state.isFilterOpen) {
        FilterScreen(
            state = state,
            searchQuery = state.searchQuery,
            yearFrom = state.yearFrom,
            yearTo = state.yearTo,
            minRating = state.minRating,
            onSearchQueryChange = {
                viewModel.onIntent(MoviesListContract.MoviesListIntent.SearchQueryChanged(it))
            },
            onYearFromChange = {
                viewModel.onIntent(MoviesListContract.MoviesListIntent.YearFromChanged(it))
            },
            onYearToChange = {
                viewModel.onIntent(MoviesListContract.MoviesListIntent.YearToChanged(it))
            },
            onMinRatingChange = {
                viewModel.onIntent(MoviesListContract.MoviesListIntent.MinRatingChanged(it))
            },
            onGenreChange = {
                viewModel.onIntent(MoviesListContract.MoviesListIntent.GenreChanged(it))
            },
            onClearAll = {
                viewModel.onIntent(MoviesListContract.MoviesListIntent.ClearFilters)
            },
            onApplyFilters = {
                viewModel.onIntent(MoviesListContract.MoviesListIntent.ApplyFilters)
            },
            onBack = {
                viewModel.onIntent(MoviesListContract.MoviesListIntent.CloseFilter)
            }
        )
    } else {
        MoviesListScreen(
            state = state,
            onMovieClick = onMovieClick,
            onSortSelected = {
                viewModel.onIntent(
                    MoviesListContract.MoviesListIntent.SortChanged(it)
                )
            },
            onFilterClick = {
                viewModel.onIntent(
                    MoviesListContract.MoviesListIntent.OpenFilter
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MoviesListScreen(
    state: MoviesListContract.UiState,
    onMovieClick: (movieId: String) -> Unit,
    onSortSelected: (SortOption) -> Unit,
    onFilterClick: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column {
                TopBarPremier(onClick = onFilterClick)
                SortChipMenu(
                    selected = state.selectedSort,
                    onOptionSelected = onSortSelected
                )
            }
        }
    ) { innerPadding ->
        when {
            state.isLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text("Loading movies...", modifier = Modifier.padding(start = 8.dp))
                }
            }

            state.error != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Info icon",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.error
                    )
                    Text("Error: ${state.error.message}", modifier = Modifier.padding(start = 8.dp))
                }
            }

            state.movies.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(Res.drawable.movienotfound),
                        contentDescription = "Movie not found",
                        modifier = Modifier.size(48.dp)
                    )
                    Text("No Movies Found", modifier = Modifier.padding(start = 8.dp))
                }
            }

            else -> ScreenContent(
                modifier = Modifier.padding(innerPadding),
                state = state,
                onMovieClick = onMovieClick
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun TopBarPremier(onClick: () -> Unit) {
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
                contentDescription = "IMDb logo"
            )
        },
        actions = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Open filters"
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
fun ScreenContent(
    modifier: Modifier = Modifier,
    state: MoviesListContract.UiState,
    onMovieClick: (movieId: String) -> Unit = { }
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Text("${state.movies.firstOrNull()?.totalItems ?: state.movies.size} movies")
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
    onClick: () -> Unit
) {
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
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            headlineContent = { Text(movie.title) },
            overlineContent = { Text(movie.releaseYear?.toString() ?: "Unknown year") },
            supportingContent = { Text(movie.genres.joinToString(", ")) },
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
                    Text("★ ${movie.rating ?: "N/A"}", color = MaterialTheme.colorScheme.primary)
                    Text("${movie.votes?.formatCompact() ?: "N/A"} votes")
                }
            },
        )
    }
}

private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"

private fun Movie.posterImageUrl(): String? {
    return posterUrl?.let { "$POSTER_BASE_URL$it" }
}

@Composable
fun FilterScreen(
    state: MoviesListContract.UiState,
    searchQuery: String,
    yearFrom: String,
    yearTo: String,
    minRating: Float,
    onSearchQueryChange: (String) -> Unit,
    onYearFromChange: (String) -> Unit,
    onYearToChange: (String) -> Unit,
    onMinRatingChange: (Float) -> Unit,
    onClearAll: () -> Unit,
    onGenreChange: (String?) -> Unit,
    onApplyFilters: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TopFilter(onBack = onBack, onClearAll = onClearAll)
        Spacer(modifier = Modifier.height(24.dp))

        SearchSection(
            value = searchQuery,
            onValueChange = onSearchQueryChange
        )

        Spacer(modifier = Modifier.height(24.dp))

        GenreSection(
            genres = state.genres,
            selectedGenre = state.selectedGenre,
            onGenreSelected = onGenreChange
        )


        Spacer(modifier = Modifier.height(24.dp))

        YearRangeSection(
            fromYear = yearFrom,
            toYear = yearTo,
            onFromYearChange = onYearFromChange,
            onToYearChange = onYearToChange
        )

        Spacer(modifier = Modifier.height(24.dp))

        MinimumRatingSection(
            rating = minRating,
            onRatingChange = onMinRatingChange
        )

        Spacer(modifier = Modifier.height(32.dp))

        ApplyFiltersButton(
            onClick = onApplyFilters
        )
    }
}

@Composable
private fun TopFilter(
    onBack: () -> Unit,
    onClearAll: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        Text(
            text = "Filter Movies",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Clear All",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.clickable(onClick = onClearAll)
        )
    }
}

@Composable
private fun SearchSection(
    value: String,
    onValueChange: (String) -> Unit
) {
    Column {
        FilterSectionTitle("SEARCH")

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search by movie title...") },
            singleLine = true,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
private fun GenreSection(
    genres: List<Genre>,
    selectedGenre: String?,
    onGenreSelected: (String?) -> Unit
) {
    Column {
        FilterSectionTitle("GENRE")

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(genres) { genre ->
                FilterChip(
                    selected = selectedGenre == genre.id,
                    onClick = {
                        onGenreSelected(
                            if (selectedGenre == genre.id) null else genre.id
                        )
                    },
                    label = {
                        Text(genre.name)
                    }
                )
            }
        }
    }
}

@Composable
private fun YearRangeSection(
    fromYear: String,
    toYear: String,
    onFromYearChange: (String) -> Unit,
    onToYearChange: (String) -> Unit
) {
    Column {
        FilterSectionTitle("YEAR RANGE")

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = fromYear,
                onValueChange = onFromYearChange,
                modifier = Modifier.weight(1f),
                label = { Text("From") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            OutlinedTextField(
                value = toYear,
                onValueChange = onToYearChange,
                modifier = Modifier.weight(1f),
                label = { Text("To") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}

@Composable
private fun MinimumRatingSection(
    rating: Float,
    onRatingChange: (Float) -> Unit
) {
    Column {
        FilterSectionTitle("MINIMUM RATING")

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Slider(
                value = rating,
                onValueChange = onRatingChange,
                valueRange = 0f..10f,
                steps = 19,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = rating.formatRating(),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun ApplyFiltersButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text("Apply Filters")
    }
}

@Composable
private fun FilterSectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

private fun Int?.formatCompact(): String {
    if (this == null) return "N/A"

    return when {
        this >= 1_000_000_000 -> compact(this / 1_000_000_000.0, "B")
        this >= 1_000_000 -> compact(this / 1_000_000.0, "M")
        this >= 1_000 -> compact(this / 1_000.0, "K")
        else -> this.toString()
    }
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

private fun Float.formatRating(): String {
    val rounded = (this * 10).toInt() / 10.0
    return rounded.toString()
}
