package com.example.bt2.repository.local

import kotlinx.coroutines.flow.Flow

interface IUserProfileDataStore {
    suspend fun saveUserProfile(userProfileModel: UserProfileModel)
    suspend fun getUserProfile(): Flow<UserProfileModel>?
}