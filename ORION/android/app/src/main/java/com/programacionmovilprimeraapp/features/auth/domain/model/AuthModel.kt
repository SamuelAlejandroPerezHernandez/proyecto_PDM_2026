package com.programacionmovilprimeraapp.features.auth.domain.model

data class RegisterRequestModel(
    val email: String,
    val password: String
)

data class RegisterResponseModel(
    val token: String,
    val id: String,
    val email: String
)

data class LoginRequestModel(
    val email: String,
    val password: String
)

data class LoginResponseModel(
    val token: String,
    val id: String,
    val email: String
)