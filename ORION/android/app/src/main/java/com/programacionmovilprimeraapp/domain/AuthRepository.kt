package com.programacionmovilprimeraapp.domain

import com.programacionmovilprimeraapp.data.dto.LoginRequestDto

interface AuthRepository{
    suspend fun RegisterNewUser(request: RegisterRequestModel): Result<RegisterResponseModel>

    suspend fun LoginUser(request: LoginRequestModel): Result<LoginResponseModel>

}