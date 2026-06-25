package com.programacionmovilprimeraapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val email: String,
    val password: String
)