package com.programacionmovilprimeraapp.features.auth.data.mapper

import com.programacionmovilprimeraapp.features.auth.data.dto.LoginRequestDto
import com.programacionmovilprimeraapp.features.auth.data.dto.LoginResponseDto
import com.programacionmovilprimeraapp.features.auth.data.dto.RegisterRequestDto
import com.programacionmovilprimeraapp.features.auth.data.dto.RegisterResponseDto
import com.programacionmovilprimeraapp.features.auth.domain.model.LoginRequestModel
import com.programacionmovilprimeraapp.features.auth.domain.model.LoginResponseModel
import com.programacionmovilprimeraapp.features.auth.domain.model.RegisterRequestModel
import com.programacionmovilprimeraapp.features.auth.domain.model.RegisterResponseModel

fun RegisterRequestModel.toRegisterRequestDto(): RegisterRequestDto{
    return RegisterRequestDto(
        email = email,
        password = password
    )
}

fun RegisterResponseDto.toRegisterResponesModel(): RegisterResponseModel{
    return RegisterResponseModel(
        token = token,
        id = id,
        email = email
    )
}

fun LoginRequestModel.toLoginRequestDto(): LoginRequestDto{
    return LoginRequestDto(
        email = email,
        password = password
    )
}

fun LoginResponseDto.toLoginResponseModel(): LoginResponseModel{
    return LoginResponseModel(
        token = token,
        id = id,
        email = email
    )
}