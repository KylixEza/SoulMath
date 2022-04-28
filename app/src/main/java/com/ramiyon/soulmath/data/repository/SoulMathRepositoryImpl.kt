package com.ramiyon.soulmath.data.repository

import com.ramiyon.soulmath.data.source.local.LocalDataSource
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import kotlinx.coroutines.flow.Flow

class SoulMathRepositoryImpl(
    private val localDataSource: LocalDataSource
): SoulMathRepository {

    override suspend fun savePrefRememberMe(isRemember: Boolean) = localDataSource.savePrefRememberMe(isRemember)

    override suspend fun savePrefHaveLoginAppBefore(isLogin: Boolean) = localDataSource.savePrefHaveLoginAppBefore(isLogin)

    override suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean) = localDataSource.savePrefHaveRunAppBefore(isFirstTime)

    override fun readPrefRememberMe(): Flow<Boolean> = localDataSource.readPrefRememberMe()

    override fun readPrefHaveLoginAppBefore(): Flow<Boolean> = localDataSource.readPrefHaveLoginAppBefore()

    override fun readPrefHaveRunAppBefore(): Flow<Boolean> = localDataSource.readPrefHaveRunAppBefore()

}