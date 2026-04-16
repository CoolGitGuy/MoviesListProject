package com.example.myapplication.data

import com.example.myapplication.domain.Movie
import com.example.myapplication.domain.MovieRepository
import com.example.myapplication.list.SortOption

class InMemoryMovieRepository : MovieRepository { // Klasa je napravljena samo kao privremeno resenje dok se ne implementira API

    private val movies = listOf(
        Movie(1, "The Shawshank Redemption", 1994, "Drama", 9.3, 2345678, "..."),
        Movie(2, "The Godfather", 1972, "Crime", 9.2, 1623456, "...")
    )

    override suspend fun getMovies(sort: SortOption): List<Movie> = movies

    override suspend fun getById(id: Int): Movie? {
        return movies.find { it.id == id }
    }
}
