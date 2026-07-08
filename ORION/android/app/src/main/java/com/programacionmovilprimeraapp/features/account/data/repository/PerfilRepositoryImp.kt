package com.programacionmovilprimeraapp.features.account.data.repository

import com.programacionmovilprimeraapp.MyApp
import com.programacionmovilprimeraapp.core.data.remote.KtorClient
import com.programacionmovilprimeraapp.features.account.data.dto.ProfileResponseDto
import com.programacionmovilprimeraapp.features.account.data.mapper.toProfileResponseModel
import com.programacionmovilprimeraapp.features.account.domain.repository.ProfileRepository
import com.programacionmovilprimeraapp.features.account.domain.model.ProfileResponseModel
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header

class ProfileRepositoryImp: ProfileRepository {
    override suspend fun getProfile(): Result<ProfileResponseModel> {
        return try {
            val token = MyApp.Companion.sessionManager.getToken()

            val response: ProfileResponseDto = KtorClient.client
                .get("api/profile/getPerfil") {
                    header("Authorization", "Bearer $token")
                }
                .body()

            Result.success(
                response.toProfileResponseModel()
            )

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}