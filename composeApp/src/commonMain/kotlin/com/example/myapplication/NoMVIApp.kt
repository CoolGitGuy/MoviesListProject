package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource

import myapplication.composeapp.generated.resources.Res
import myapplication.composeapp.generated.resources.compose_multiplatform
import myapplication.composeapp.generated.resources.logoimdb
import org.jetbrains.compose.resources.DrawableResource


@Composable
fun NoMVIApp() {
    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        MyScreen()
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen() {
    Scaffold(
        topBar = {
            Column {
                TopBarPremier()
                SortChipMenu()
            }
        }
    ) { innerPadding ->
        Column {
            Text("Aaaaaa")
            ScreenContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun SortChipMenu() {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf("Rating") }

    Box {
        SuggestionChip(
            onClick = { expanded = true },
            label = { Text("Sort: $selected") },
            icon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Ikona"
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listOf("Rating", "Year", "Title","Popularity").forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selected = option
                        expanded = false
                    }
                )
            }
        }
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
fun ScreenContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.background(Color.DarkGray).verticalScroll(rememberScrollState())) {
        Text("100 movies")
        repeat(20) {
            MovieListItem(headline = "The Godfather", overline = "1972", supporting = "Drama", trailing1 = "9.2", trailing2 = "2.2M votes")
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