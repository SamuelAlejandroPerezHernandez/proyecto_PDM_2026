package com.programacionmovilprimeraapp.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

// pide permiso para crear un archivo fisico
class SessionManager private constructor(AuthContext: Context){

    // crea la llave para ese archivo
    private val masterKey = MasterKey.Builder(AuthContext)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()


    // crea ese archivo
    private val sharedPreferences = EncryptedSharedPreferences.create(
        AuthContext.applicationContext,
        "security_user_data",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    // crea el nombre de la columna de la tabla
    companion object{
        private const val TOKEN_KEY = "JWT_TOKEN"

        @Volatile
        private var INSTANCE: SessionManager? = null

        fun getInstance(context: Context): SessionManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SessionManager(context).also { INSTANCE = it }
            }
        }
    }

    // guarda y encripta el token
    fun saveToken(token: String){
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(): String?{
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun clearSession(){
        sharedPreferences.edit().clear().apply()
    }
}