package com.programacionmovilprimeraapp.features.account.data.mapper


import com.programacionmovilprimeraapp.features.account.data.dto.ProfileResponseDto
import com.programacionmovilprimeraapp.features.account.domain.model.ProfileResponseModel

fun ProfileResponseDto.toProfileResponseModel(): ProfileResponseModel {
    return ProfileResponseModel(
        email = this.email
    )
}