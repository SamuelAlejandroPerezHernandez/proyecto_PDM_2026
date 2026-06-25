package com.programacionmovilprimeraapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String,
    val id: String,
    val email: String
)