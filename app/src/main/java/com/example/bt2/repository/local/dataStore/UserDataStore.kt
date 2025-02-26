package com.example.bt2.repository.local.dataStore

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
        val uriImage = stringPreferencesKey("UriImage")
        val phone = stringPreferencesKey("Phone")
        val gender = stringPreferencesKey("Gender")
        val country = stringPreferencesKey("Country")
    }

    override suspend fun saveUser(userModel: UserModel) {
        context.dataStore.edit { user ->
            user[name] = userModel.name
            user[email] = userModel.email
            user[password] = userModel.password
            user[uriImage] = userModel.uriImage
            user[phone] = userModel.phone
            user[gender] = userModel.gender
            user[country] = userModel.country
        }
    }

    override suspend fun getUser(): Flow<UserModel> = context.dataStore.data.map { user ->
        UserModel(user[name] ?: "", user[email] ?: "", user[password] ?: "", user[uriImage] ?: "", user[phone] ?: "", user[gender] ?: "", user[country] ?: "" )
    }


}