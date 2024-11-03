package com.example.gangapackagesolution.repository

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class TokenManagement(context: Context) {

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context, "secure_prefs",
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                                                                     )

    fun saveToken(token: String) {
        sharedPreferences.edit().putString("jwtToken", token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("jwtToken", null)
    }

    fun newUser(newUser: Boolean) {
        sharedPreferences.edit().putBoolean("newUser", newUser).apply()
    }

    fun clearNewUser() {
        sharedPreferences.edit().remove("newUser").apply()
    }

    fun isNewUser(): Boolean {
        return sharedPreferences.getBoolean("newUser", false)
    }

    fun deleteToken() {
        sharedPreferences.edit().remove("jwtToken").apply()
    }

    // Saving email
    fun saveEmail(email: String) {
        sharedPreferences.edit().putString("email", email).apply()
    }

    // Getting email
    fun getEmail(): String? {
        return sharedPreferences.getString("email", null)
    }

    fun setColor(color: Color) {
        // Convert color to hex string and store in SharedPreferences
        val colorHex = String.format("#%08X", color.toArgb())
        sharedPreferences.edit().putString("Color", colorHex).apply()
    }

    fun getColor(): Color {
        // Retrieve the color string from SharedPreferences
        val colorHex = sharedPreferences.getString("Color", "#FF673AB7") ?: "#FF673AB7"
        // Convert the hex string back to Color object
        return Color(android.graphics.Color.parseColor(colorHex))
    }
}
