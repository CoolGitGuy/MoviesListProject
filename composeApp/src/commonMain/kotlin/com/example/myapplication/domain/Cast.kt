package com.example.myapplication.domain

import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    val id: String,
    val name: String,
    val profileUrl: String? = null
)