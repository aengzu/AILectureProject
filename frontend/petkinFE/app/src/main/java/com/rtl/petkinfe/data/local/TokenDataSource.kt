package com.rtl.petkinfe.data.local
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class TokenDataSource(private val context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: TokenDataSource? = null

        fun getInstance(context: Context): TokenDataSource {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: TokenDataSource(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
    private val Context.dataStore by preferencesDataStore(name = "user_prefs")
    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")

    // Save token
    suspend fun saveToken(accessToken: String, refreshToken: String) {
        context.dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = accessToken
            prefs[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    suspend fun getAccessTokenImmediately(): String? {
        return getAccessToken().first() // Flow 값을 즉시 가져오기
    }

    suspend fun getRefreshTokenImmediately(): String? {
        return getRefreshToken().first() // Flow 값을 즉시 가져오기
    }


    // Get token
    fun getAccessToken(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN_KEY]
        }
    }

    fun getRefreshToken(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[REFRESH_TOKEN_KEY]
        }
    }
    // Check if logged in
    fun isLoggedIn(): Flow<Boolean> {
        return getAccessToken().map { accessToken ->
            !accessToken.isNullOrEmpty()
        }
    }

    // Clear token
    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN_KEY)
            prefs.remove(REFRESH_TOKEN_KEY)
        }
    }
}
