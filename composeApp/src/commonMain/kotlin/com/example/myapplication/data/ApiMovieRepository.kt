package com.example.myapplication.data

import com.example.myapplication.domain.Movie
import com.example.myapplication.domain.MovieRepository
import com.example.myapplication.list.SortOption
import com.example.myapplication.networking.MovieApi
import com.example.myapplication.networking.model.MovieListItem

class ApiMovieRepository(
    private val api : MovieApi
) : MovieRepository {
    override suspend fun getMovies(sort: SortOption): List<Movie> {
        val response = api.getMovies(
            sortBy = sort.toApiSortBy(),
            sortOrder = sort.toApiSortOrder()
        )

        return response.items.map { it.toDomain() }
    }



    override suspend fun getById(id: String): Movie? {
        TODO("Not yet implemented")
    }
}

private fun SortOption.toApiSortBy(): String {
    return when (this) {
        SortOption.Rating -> "imdb_rating"
        SortOption.Year -> "year"
        SortOption.Title -> "title"
        SortOption.Popularity -> "popularity"
    }
}

private fun SortOption.toApiSortOrder(): String {
    return when (this) {
        SortOption.Title -> "asc"
        SortOption.Rating,
        SortOption.Year,
        SortOption.Popularity -> "desc"
    }
}

private fun MovieListItem.toDomain(): Movie {
    return Movie(
        id = imdbId,
        title = title,
        releaseYear = year,
        genres = genres?.map { it.name } ?: emptyList(),
        rating = imdbRating,
        votes = imdbVotes,
        posterUrl = posterPath
    )
}
