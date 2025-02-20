package com.example.bt2.repository.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val  LAYOUT_PREFERENCES_NAME = "layout_preferences2"

val Context.dataStoreProfile: DataStore<Preferences> by preferencesDataStore(name = LAYOUT_PREFERENCES_NAME)

class UserDataProfileStore(private val context: Context) : IUserProfileDataStore {

    companion object{
        val uriImage = stringPreferencesKey("uriImage")
        val phone = stringPreferencesKey("phone")
        val gender = stringPreferencesKey("gender")
    }


    override suspend fun saveUserProfile(userProfileModel: UserProfileModel) {
        context.dataStoreProfile.edit { user ->
            user[uriImage] = userProfileModel.uriImage
            user[phone] = userProfileModel.phone
            user[gender] = userProfileModel.gender
        }
    }

    override suspend fun getUserProfile(): Flow<UserProfileModel> = context.dataStore.data.map { user ->
        UserProfileModel(user[uriImage] ?: "", user[phone] ?: "", user[gender] ?: "")
    }


}