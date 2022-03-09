package com.laco.sample.architecture.local.source

import android.content.SharedPreferences
import androidx.core.content.edit
import com.laco.sample.architecture.data.source.AuthLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AuthLocalDataSourceImpl @Inject constructor(
    private val pref: SharedPreferences
) : AuthLocalDataSource {

    override suspend fun getHashId(): Result<String?> = runCatching {
        withContext(Dispatchers.IO) {
            pref.getString(KEY_HASH_ID, null)
        }
    }

    override suspend fun saveHashId(hashId: String): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            pref.edit { putString(KEY_HASH_ID, hashId) }
        }
    }

    companion object {
        private const val KEY_HASH_ID = "KEY_HASH_ID"
    }
}
