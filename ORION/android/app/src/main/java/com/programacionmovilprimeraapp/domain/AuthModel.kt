package com.programacionmovilprimeraapp.domain

data class RegisterRequestModel(
    val email: String,
    val password: String
)

data class RegisterResponseModel(
    val token: String,
    val id: String,
    val email: String
)

