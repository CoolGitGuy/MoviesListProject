package com.example.myapplication.domain

import kotlinx.serialization.Serializable

@Serializable
data class Movie(val id: Int,
                 val title: String,
                 val releaseYear: Int,
                 val genre: String,
                 val rating: Double,
                 val votes: Int,
                 val posterUrl: String)
