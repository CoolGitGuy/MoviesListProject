package com.example.myapplication

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MoviesApp() {
    val darkTheme = isSystemInDarkTheme()

    MaterialTheme(
        colorScheme = if (darkTheme) darkColorScheme(
            primary = Color(0xFFF5C518),
            onPrimary = Color(0xFF111111),
            background = Color(0xFF121212),
            onBackground = Color(0xFFF3F3F3),
            surface = Color(0xFF1E1E1E),
            onSurface = Color(0xFFF3F3F3),
            surfaceVariant = Color(0xFF2A2A2A),
            onSurfaceVariant = Color(0xFFBDBDBD),
            error = Color(0xFFFF6B6B)
        ) else lightColorScheme(
            primary = Color(0xFFF5C518),
            onPrimary = Color(0xFF111111),
            background = Color(0xFFF6F3EA),
            onBackground = Color(0xFF1A1A1A),
            surface = Color(0xFFFFFCF5),
            onSurface = Color(0xFF1A1A1A),
            surfaceVariant = Color(0xFFE9E2D0),
            onSurfaceVariant = Color(0xFF5F584B),
            error = Color(0xFFB3261E)
        )
    ) {
        MoviesNavigation("movies")
    }
}
