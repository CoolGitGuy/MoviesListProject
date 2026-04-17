package com.example.myapplication.domain

import kotlinx.serialization.Serializable

@Serializable
data class Movie(val id: String,
                 val title: String,
                 val releaseYear: Int?,
                 val genres: List<String>,
                 val rating: Float?,
                 val votes: Int?,
                 val posterUrl: String?,
                 var totalItems : Int? = null)

