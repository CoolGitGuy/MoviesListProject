package com.example.myapplication.data

import com.example.myapplication.domain.Cast
import com.example.myapplication.domain.Movie
import com.example.myapplication.domain.MovieDetails
import com.example.myapplication.domain.MovieRepository
import com.example.myapplication.list.SortOption
import com.example.myapplication.networking.MovieApi
import com.example.myapplication.networking.model.MovieDetailApiModel
import com.example.myapplication.networking.model.MovieImagesAPI
import com.example.myapplication.networking.model.MovieListItem
import com.example.myapplication.networking.model.PersonSummary

class ApiMovieRepository(
    private val api : MovieApi
) : MovieRepository {
    override suspend fun getMovies(sort: SortOption): List<Movie> {
        val response = api.getMovies(
            sortBy = sort.toApiSortBy(),
            sortOrder = sort.toApiSortOrder()
        )
        var movies = response.items.map { it.toDomain() }
        movies.get(0).totalItems = response.totalItems

        return movies
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

    override suspend fun getMovieCast(id: String): List<Cast> {
        val response = api.getMovieCast(id)

        return response.items.map { it.toDomain() }
    }

    override suspend fun searchImages(id: String): List<String>? {
        val response = api.getMovieImages(id)
        return response.backdrops?.map{ it.filePath }
    }
}

private fun SortOption.toApiSortBy(): String {
    return when (this) {
        SortOption.RatingDESC -> "imdb_rating"
        SortOption.RatingASC -> "imdb_rating"
        SortOption.YearDESC -> "year"
        SortOption.YearASC -> "year"
        SortOption.TitleDESC -> "title"
        SortOption.TitleASC -> "title"
        SortOption.PopularityDESC -> "popularity"
        SortOption.PopularityASC -> "popularity"
    }
}

private fun SortOption.toApiSortOrder(): String {
    return when (this) {
        SortOption.TitleDESC -> "desc"
        SortOption.TitleASC -> "asc"
        SortOption.RatingASC -> "asc"
        SortOption.RatingDESC -> "desc"
        SortOption.YearASC -> "asc"
        SortOption.YearDESC -> "desc"
        SortOption.PopularityDESC -> "desc"
        SortOption.PopularityASC -> "asc"
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

private fun PersonSummary.toDomain(): Cast {
    return Cast(
        id = imdbId,
        name = name,
        profileUrl = profilePath
    )
}

