package com.programacionmovilprimeraapp.data.mapper

import com.programacionmovilprimeraapp.data.dto.LoginRequestDto
import com.programacionmovilprimeraapp.data.dto.LoginResponseDto
import com.programacionmovilprimeraapp.data.dto.RegisterRequestDto
import com.programacionmovilprimeraapp.data.dto.RegisterResponseDto
import com.programacionmovilprimeraapp.domain.LoginRequestModel
import com.programacionmovilprimeraapp.domain.LoginResponseModel
import com.programacionmovilprimeraapp.domain.RegisterRequestModel
import com.programacionmovilprimeraapp.domain.RegisterResponseModel

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