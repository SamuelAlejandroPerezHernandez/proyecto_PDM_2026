package com.programacionmovilprimeraapp.features.account.data.dto


import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponseDto(
    val email: String
)