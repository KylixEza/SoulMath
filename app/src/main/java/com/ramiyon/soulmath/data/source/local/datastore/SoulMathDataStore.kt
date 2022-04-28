package com.ramiyon.soulmath.data.source.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SoulMathDataStore(private val context: Context) {
    private val Context.userPreferenceDataStore: DataStore<Preferences> by preferencesDataStore(
        name = DataStoreUtil.DATA_STORE_NAME
    )

    private suspend fun saveBooleanValue(value: Boolean, key: Preferences.Key<Boolean>) {
        context.userPreferenceDataStore.edit {
            it[key] = value
        }
    }

    suspend fun savePrefRememberMe(isRemember: Boolean) {
        saveBooleanValue(isRemember, DataStoreUtil.REMEMBER_ME)
    }

    suspend fun savePrefHaveLoginAppBefore(isLogin: Boolean) {
        saveBooleanValue(isLogin, DataStoreUtil.HAVE_LOGIN_APP_BEFORE)
    }

    suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean) {
        saveBooleanValue(isFirstTime, DataStoreUtil.HAVE_RUN_APP_BEFORE)
    }

    fun readPrefRememberMe(): Flow<Boolean> = context.userPreferenceDataStore.data.map {
        it[DataStoreUtil.REMEMBER_ME] != null
    }

    fun readPrefHaveLoginAppBefore(): Flow<Boolean> = context.userPreferenceDataStore.data.map {
        it[DataStoreUtil.HAVE_LOGIN_APP_BEFORE] != null
    }

    fun readPrefHaveRunAppBefore(): Flow<Boolean> = context.userPreferenceDataStore.data.map {
        it[DataStoreUtil.HAVE_RUN_APP_BEFORE] != null
    }
}