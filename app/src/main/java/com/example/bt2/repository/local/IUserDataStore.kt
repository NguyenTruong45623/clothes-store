package com.example.bt2.repository.local

import kotlinx.coroutines.flow.Flow

interface IUserDataStore {
    suspend fun saveUser(userModel: UserModel)
    suspend fun getUser(): Flow<UserModel>?
}