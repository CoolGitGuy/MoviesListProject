package com.example.myapplication.data

import com.example.myapplication.domain.Movie
import com.example.myapplication.domain.MovieRepository
import com.example.myapplication.list.SortOption

class InMemoryMovieRepository  { // Klasa je napravljena samo kao privremeno resenje dok se ne implementira API

    private val movies = listOf(
        Movie(1.toString(), "The Shawshank Redemption", 1994, listOf("Drama"), 9.3f, 2345678, "https://example.com/poster1.jpg"),
        Movie(2.toString(), "The Godfather", 1972, listOf("Crime", "Drama"), 9.2f, 1623456, "https://example.com/poster2.jpg"),
        Movie(3.toString(), "The Dark Knight", 2008, listOf("Action", "Crime", "Drama"), 9.0f, 2345678, "https://example.com/poster3.jpg"),
        Movie(4.toString(), "Pulp Fiction", 1994, listOf("Crime", "Drama"), 8.9f, 1789456, "https://example.com/poster4.jpg"),
        Movie(5.toString(), "Schindler's List", 1993, listOf("Biography", "Drama", "History"), 8.9f, 1234567, "https://example.com/poster5.jpg")
    )
    //override suspend fun getMovies(sort: SortOption): List<Movie> = movies

    /*override suspend fun getById(id: String): Movie? {
        return movies.find { it.id == id }
    }*/
}
