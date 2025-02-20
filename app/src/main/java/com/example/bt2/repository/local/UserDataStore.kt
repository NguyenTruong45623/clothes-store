package com.example.bt2.repository.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val  LAYOUT_PREFERENCES_NAME = "layout_preferences"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = LAYOUT_PREFERENCES_NAME)

class UserDataStore(private val context: Context) : IUserDataStore {

    companion object{
        val name = stringPreferencesKey("Name")
        val email = stringPreferencesKey("Email")
        val password = stringPreferencesKey("PassWord")
    }

    override suspend fun saveUser(userModel: UserModel) {
        context.dataStore.edit { user ->
            user[name] = userModel.name
            user[email] = userModel.email
            user[password] = userModel.password
        }
    }

    override suspend fun getUser(): Flow<UserModel> = context.dataStore.data.map { user ->
        UserModel(user[name] ?: "", user[email] ?: "", user[password] ?: "")
    }


}