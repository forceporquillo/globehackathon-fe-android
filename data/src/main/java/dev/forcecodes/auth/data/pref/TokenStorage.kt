package dev.forcecodes.auth.data.pref

interface TokenStorage {
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
}
