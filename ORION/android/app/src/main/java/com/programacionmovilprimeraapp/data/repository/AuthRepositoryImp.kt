package com.programacionmovilprimeraapp.data.repository

import com.programacionmovilprimeraapp.data.dto.LoginResponseDto
import com.programacionmovilprimeraapp.data.dto.RegisterResponseDto
import com.programacionmovilprimeraapp.data.mapper.toLoginRequestDto
import com.programacionmovilprimeraapp.data.mapper.toLoginResponseModel
import com.programacionmovilprimeraapp.data.mapper.toRegisterRequestDto
import com.programacionmovilprimeraapp.data.mapper.toRegisterResponesModel
import com.programacionmovilprimeraapp.data.remote.KtorClient
import com.programacionmovilprimeraapp.domain.AuthRepository
import com.programacionmovilprimeraapp.domain.LoginRequestModel
import com.programacionmovilprimeraapp.domain.LoginResponseModel
import com.programacionmovilprimeraapp.domain.RegisterRequestModel
import com.programacionmovilprimeraapp.domain.RegisterResponseModel
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthRepositoryImp: AuthRepository{
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