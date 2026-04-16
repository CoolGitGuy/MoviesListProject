package com.example.myapplication.networking

import com.example.myapplication.networking.model.MovieDetailApiModel
import com.example.myapplication.networking.model.MovieListItem
import com.example.myapplication.networking.model.MovieTrailerApiModel
import com.example.myapplication.networking.model.PaginatedResponse
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query

interface MovieApi {
    @GET("movies")
    suspend fun getMovies(
        @Query("page") page: Int? = 1, // Page number (must be >= 1).
        @Query("page_size") pageSize: Int? = 20, // Items per page (1-100).
        @Query("query") title: String? = null, // Search movies by title (case-insensitive substring match).
        @Query("genre_id") genre: String? = null, // Filter by genre ID (see /genres for valid IDs).
        @Query("min_year") minYear: Int? = null, // Filter movies released in or after this year.
        @Query("max_year") maxYear: Int? = null, // Filter movies released in or before this year.
        @Query("min_rating") minRating: Float? = null, // Filter movies with IMDb rating >= this value (0.0-10.0).
        @Query("sort_by") sortBy: String? = "imdb_votes", // Sort field. One of: imdb_votes, year, imdb_rating, tmdb_rating, popularity, title
        @Query("sort_order") sortOrder: String? = "desc" // Sort direction: asc or desc.
    ): PaginatedResponse<MovieListItem>

    @GET("movies/{id}")
    suspend fun getMovieById(@Path("id") id: String): MovieDetailApiModel

    @GET("movies/{id}/videos")
    suspend fun getMovieTrailer(
        @Path("id") id: String,
        @Query("type") type: String? = "Trailer" // stavio sam default na Trailer jer nam samo on treba
    ): List<MovieTrailerApiModel>

}
