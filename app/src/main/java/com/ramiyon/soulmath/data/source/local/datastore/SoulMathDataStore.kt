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

    private suspend fun<T> saveValue(value: T, key: Preferences.Key<T>) {
        context.userPreferenceDataStore.edit {
            it[key] = value
        }
    }

    suspend fun savePrefRememberMe(isRemember: Boolean) {
        saveValue(isRemember, DataStoreUtil.REMEMBER_ME)
    }

    suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean) {
        saveValue(isFirstTime, DataStoreUtil.HAVE_RUN_APP_BEFORE)
    }

    suspend fun savePrefStudentId(studentId: String) {
        saveValue(studentId, DataStoreUtil.STUDENT_ID)
    }

    fun readPrefRememberMe(): Flow<Boolean> = context.userPreferenceDataStore.data.map {
        it[DataStoreUtil.REMEMBER_ME] ?: false
    }

    fun readPrefHaveRunAppBefore(): Flow<Boolean> = context.userPreferenceDataStore.data.map {
        it[DataStoreUtil.HAVE_RUN_APP_BEFORE] ?: false
    }

    fun readPrefStudentId(): Flow<String?> = context.userPreferenceDataStore.data.map {
        it[DataStoreUtil.STUDENT_ID]
    }
}