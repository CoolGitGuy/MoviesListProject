package com.example.myapplication.domain

import com.example.myapplication.list.SortOption

interface MovieRepository {
    suspend fun getMovies(sort: SortOption): List<Movie>
    suspend fun getById(id: Int): Movie?
}
