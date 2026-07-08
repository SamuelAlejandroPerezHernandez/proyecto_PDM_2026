package com.programacionmovilprimeraapp.features.auth.data.repository

import com.programacionmovilprimeraapp.core.data.remote.KtorClient
import com.programacionmovilprimeraapp.features.auth.data.mapper.toLoginRequestDto
import com.programacionmovilprimeraapp.features.auth.data.mapper.toLoginResponseModel
import com.programacionmovilprimeraapp.features.auth.data.mapper.toRegisterRequestDto
import com.programacionmovilprimeraapp.features.auth.data.mapper.toRegisterResponesModel
import com.programacionmovilprimeraapp.features.auth.data.dto.LoginResponseDto
import com.programacionmovilprimeraapp.features.auth.data.dto.RegisterResponseDto
import com.programacionmovilprimeraapp.features.auth.domain.model.LoginRequestModel
import com.programacionmovilprimeraapp.features.auth.domain.model.LoginResponseModel
import com.programacionmovilprimeraapp.features.auth.domain.model.RegisterRequestModel
import com.programacionmovilprimeraapp.features.auth.domain.model.RegisterResponseModel
import com.programacionmovilprimeraapp.features.auth.domain.repository.AuthRepository
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthRepositoryImp: AuthRepository {
    override suspend fun RegisterNewUser(request: RegisterRequestModel): Result<RegisterResponseModel>{
        return try{
            val request = request.toRegisterRequestDto()

            val response: RegisterResponseDto = KtorClient.client
                .post("api/auth/register"){
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
                .body()

            Result.success(
                response.toRegisterResponesModel()
            )

        }catch(e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun LoginUser(request: LoginRequestModel): Result<LoginResponseModel> {
        return try{
            val request = request.toLoginRequestDto()

            val response: LoginResponseDto = KtorClient.client
                .post("api/auth/login"){
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
                .body()

            Result.success(
                response.toLoginResponseModel()
            )

        }catch(e: Exception){
            Result.failure(e)
        }
    }
}