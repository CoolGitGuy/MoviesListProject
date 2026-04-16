package com.example.myapplication.data

import com.example.myapplication.domain.Movie
import com.example.myapplication.domain.MovieDetails
import com.example.myapplication.domain.MovieRepository
import com.example.myapplication.list.SortOption
import com.example.myapplication.networking.MovieApi
import com.example.myapplication.networking.model.MovieDetailApiModel
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



    override suspend fun getMovieDetails(id: String): MovieDetails {
            return api.getMovieById(id).toDomain()
    }

    override suspend fun getMovieTrailer(id: String): String {
        return api.getMovieTrailer(id)
            .firstOrNull { it.site == "YouTube" }
            ?.key
            ?: ""
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

private fun MovieDetailApiModel.toDomain(): MovieDetails {
    return MovieDetails(
        id = imdbId,
        tmdbId = tmdbId,
        title = title,
        originalTitle = originalTitle,
        overview = overview,
        tagline = tagline,
        releaseDate = releaseDate,
        year = year,
        runtime = runtime,
        budget = budget,
        revenue = revenue,
        languageCode = languageCode,
        popularity = popularity,
        imdbRating = imdbRating,
        imdbVotes = imdbVotes,
        tmdbRating = tmdbRating,
        tmdbVotes = tmdbVotes,
        posterUrl = posterPath,
        backdropUrl = backdropPath,
        homepage = homepage,
        genres = genres.map { it.name },
        collectionName = collection?.name,
        collectionPosterUrl = collection?.posterPath,
        collectionBackdropUrl = collection?.backdropPath
    )
}

