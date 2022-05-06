package com.ramiyon.soulmath.data.repository

import com.ramiyon.soulmath.data.mechanism.NetworkBoundRequest
import com.ramiyon.soulmath.data.source.local.LocalDataSource
import com.ramiyon.soulmath.data.source.remote.RemoteDataSource
import com.ramiyon.soulmath.data.source.remote.RemoteResponse
import com.ramiyon.soulmath.data.source.remote.api.response.UserBody
import com.ramiyon.soulmath.data.source.remote.api.response.UserResponse
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import kotlinx.coroutines.flow.Flow

class SoulMathRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): SoulMathRepository {

    override suspend fun savePrefRememberMe(isRemember: Boolean) = localDataSource.savePrefRememberMe(isRemember)

    override suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean) = localDataSource.savePrefHaveRunAppBefore(isFirstTime)

    override fun readPrefRememberMe(): Flow<Boolean> = localDataSource.readPrefRememberMe()

    override fun readPrefHaveRunAppBefore(): Flow<Boolean> = localDataSource.readPrefHaveRunAppBefore()

    override fun signUp(email: String, password: String, body: UserBody)  =
        object : NetworkBoundRequest<UserResponse>() {
            override suspend fun createCall(): Flow<RemoteResponse<UserResponse>> {
                return remoteDataSource.signUp(email, password, body)
            }

            override suspend fun saveCallResult(data: UserResponse) {
                TODO("Not yet implemented")
            }
        }.asFlow()

    override fun signIn(email: String, password: String) =
        object : NetworkBoundRequest<UserResponse>() {
            override suspend fun createCall(): Flow<RemoteResponse<UserResponse>> {
                return remoteDataSource.signIn(email, password)
            }

            override suspend fun saveCallResult(data: UserResponse) {
                TODO("Not yet implemented")
            }

        }.asFlow()
}