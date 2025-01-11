package dev.forcecodes.auth.data.pref

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject

class KeyStoreTokenStorage @Inject constructor(
    @ApplicationContext private val context: Context
) : TokenStorage {

    private val keystore: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private val keyAlias = "jwt_key_alias"

    private val sharedPreferences by lazy  {
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    }

    override fun saveToken(token: String) {
        val cipher = getAesCipher()

        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateKey())
        val encryptedToken = cipher.doFinal(token.toByteArray(Charsets.UTF_8))

        val encryptedTokenBase64 = Base64.encodeToString(encryptedToken, Base64.DEFAULT)

        with(sharedPreferences.edit()) {
            putString(ENCRYPTED_TOKEN_KEY, encryptedTokenBase64)
            apply()
        }
    }

    override fun getToken(): String? {

        val encryptedTokenBase64 = sharedPreferences.getString(ENCRYPTED_TOKEN_KEY, null) ?: return null
        val encryptedToken = Base64.decode(encryptedTokenBase64, Base64.DEFAULT)

        val cipher = getAesCipher()
        cipher.init(Cipher.DECRYPT_MODE, getOrCreateKey())

        val decryptedTokenBytes = cipher.doFinal(encryptedToken)

        return String(decryptedTokenBytes, Charsets.UTF_8)
    }

    override fun clearToken() {
        with(sharedPreferences.edit()) {
            remove(ENCRYPTED_TOKEN_KEY)
            apply()
        }
    }

    private fun getOrCreateKey(): SecretKey {
        return if (keystore.containsAlias(keyAlias)) {
            keystore.getKey(keyAlias, null) as SecretKey
        } else {
            createKey()
        }
    }

    private fun createKey(): SecretKey {
        val keyGenerator =
            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        keyGenerator.init(
            KeyGenParameterSpec.Builder(
                keyAlias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
        )
        return keyGenerator.generateKey() as SecretKey
    }

    private fun getAesCipher(): Cipher {
        return Cipher.getInstance("AES/GCM/NoPadding")
    }
}

private const val ENCRYPTED_TOKEN_KEY = "encrypted_jwt_token"
