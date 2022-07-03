package com.sdiegas.zkbchallenge.data.local

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RegistrationDataStore @Inject constructor(@ApplicationContext context: Context) {
    private val spec = KeyGenParameterSpec.Builder(
        "_androidx_security_master_key_",
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setKeySize(256)
        .build()

    private val masterKeyAlias = MasterKey.Builder(context).setKeyGenParameterSpec(spec)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secret_shared_prefs",
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    @OptIn(ExperimentalSerializationApi::class)
    fun getRegistrationData(): RegistrationData? {
        return if (sharedPreferences.contains("registration_data_key")) {
            Json.decodeFromString(sharedPreferences.getString("registration_data_key", "") ?: "")
        } else null
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun setRegistrationData(data: RegistrationData) {
        sharedPreferences.edit().putString("registration_data_key", Json.encodeToString(data)).apply()
    }


}