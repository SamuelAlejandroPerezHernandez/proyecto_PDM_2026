package com.programacionmovilprimeraapp.features.auth.domain.repository

import com.programacionmovilprimeraapp.features.auth.domain.model.LoginRequestModel
import com.programacionmovilprimeraapp.features.auth.domain.model.LoginResponseModel
import com.programacionmovilprimeraapp.features.auth.domain.model.RegisterRequestModel
import com.programacionmovilprimeraapp.features.auth.domain.model.RegisterResponseModel

interface AuthRepository{
    suspend fun RegisterNewUser(request: RegisterRequestModel): Result<RegisterResponseModel>

    suspend fun LoginUser(request: LoginRequestModel): Result<LoginResponseModel>

}