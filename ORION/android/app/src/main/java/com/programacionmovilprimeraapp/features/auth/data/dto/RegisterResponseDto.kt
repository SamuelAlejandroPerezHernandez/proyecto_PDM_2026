package com.programacionmovilprimeraapp.features.auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDto(
    val token: String,
    val id: String,
    val email: String
)