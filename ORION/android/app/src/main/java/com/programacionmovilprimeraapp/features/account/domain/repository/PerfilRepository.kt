package com.programacionmovilprimeraapp.features.account.domain.repository

import com.programacionmovilprimeraapp.features.account.domain.model.ProfileResponseModel

interface ProfileRepository {
    suspend fun getProfile(): Result<ProfileResponseModel>
}