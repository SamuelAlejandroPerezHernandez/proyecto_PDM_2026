package com.programacionmovilprimeraapp.core.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.json.JSONObject
import java.io.File

// pide permiso para crear un archivo fisico
class SessionManager private constructor(private val authContext: Context) {

    companion object {
        private const val TAG = "SessionManager"
        private const val PREFS_FILE_NAME = "security_user_data"
        private const val TOKEN_KEY = "JWT_TOKEN"
        private const val EMAIL_KEY = "USER_EMAIL"

        @Volatile
        private var INSTANCE: SessionManager? = null

        fun getInstance(context: Context): SessionManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SessionManager(context).also { INSTANCE = it }
            }
        }
    }

    // guarda y encripta el token
    // Se crea de forma "perezosa" (lazy) para poder interceptar el error de
    // creación y reintentar, en vez de crashear la app en el arranque (MyApp.onCreate).
    private val sharedPreferences: SharedPreferences by lazy {
        createEncryptedPrefsSafely()
    }

    private fun buildMasterKey(): MasterKey {
        return MasterKey.Builder(authContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private fun buildEncryptedPrefs(masterKey: MasterKey): SharedPreferences {
        return EncryptedSharedPreferences.create(
            authContext.applicationContext,
            PREFS_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    /**
     * Intenta crear el EncryptedSharedPreferences normalmente.
     * Si falla porque la llave del Keystore quedó desincronizada del archivo
     * cifrado (AEADBadTagException / KeyStoreException: Signature/MAC verification
     * failed), borra el archivo corrupto y lo vuelve a crear desde cero.
     */
    private fun createEncryptedPrefsSafely(): SharedPreferences {
        return try {
            buildEncryptedPrefs(buildMasterKey())
        } catch (e: Exception) {
            Log.w(TAG, "Fallo al abrir EncryptedSharedPreferences, se " +
                    "recreará el almacenamiento local. Causa: ${e.message}")
            deleteCorruptedPrefsFile()
            buildEncryptedPrefs(buildMasterKey())
        }
    }

    private fun deleteCorruptedPrefsFile() {
        try {
            authContext.deleteSharedPreferences(PREFS_FILE_NAME)
            val prefsFile = File(
                authContext.applicationContext.filesDir.parentFile,
                "shared_prefs/$PREFS_FILE_NAME.xml"
            )
            if (prefsFile.exists()) {
                prefsFile.delete()
            }
        } catch (e: Exception) {
            Log.e(TAG, "No se pudo borrar el archivo de preferencias corrupto", e)
        }
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    // Guarda el correo del usuario autenticado para poder mostrarlo
    // luego en la pantalla "Mi cuenta" sin tener que pedirlo de nuevo al backend.
    fun saveEmail(email: String) {
        sharedPreferences.edit().putString(EMAIL_KEY, email).apply()
    }

    // Si el correo no se guardó explícitamente (por ejemplo, si la sesión
    // ya estaba activa antes de agregar saveEmail en el login), se recupera
    // directamente del token JWT, que ya lo trae en su payload. Así no es
    // necesario cerrar sesión y volver a entrar para verlo.
    fun getEmail(): String? {
        val storedEmail = sharedPreferences.getString(EMAIL_KEY, null)
        if (storedEmail != null) return storedEmail

        val token = getToken() ?: return null
        val decodedEmail = decodeEmailFromToken(token)
        if (decodedEmail != null) {
            saveEmail(decodedEmail)
        }
        return decodedEmail
    }

    private fun decodeEmailFromToken(token: String): String? {
        return try {
            val parts = token.split(".")
            if (parts.size < 2) return null

            var payload = parts[1]
            // El JWT usa Base64URL sin padding; hay que completarlo antes de decodificar.
            val remainder = payload.length % 4
            if (remainder != 0) {
                payload += "=".repeat(4 - remainder)
            }

            val decodedBytes = Base64.decode(payload, Base64.URL_SAFE)
            val json = JSONObject(String(decodedBytes, Charsets.UTF_8))
            val email = json.optString("email", "")
            email.ifBlank { null }
        } catch (e: Exception) {
            Log.e(TAG, "No se pudo decodificar el email desde el token", e)
            null
        }
    }

    fun clearSession() {
        sharedPreferences.edit().clear().apply()
    }
}