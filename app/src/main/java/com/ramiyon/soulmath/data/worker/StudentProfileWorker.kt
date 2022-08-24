package com.ramiyon.soulmath.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ramiyon.soulmath.data.source.local.LocalDataSource
import com.ramiyon.soulmath.data.source.remote.RemoteDataSource
import com.ramiyon.soulmath.data.util.LocalAnswer
import com.ramiyon.soulmath.util.toStudentBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StudentProfileWorker(
    ctx: Context,
    params: WorkerParameters
): CoroutineWorker(ctx, params), KoinComponent {

    private val localDataSource by inject<LocalDataSource>()
    private val remoteDataSource by inject<RemoteDataSource>()

    override suspend fun doWork(): Result {
        var result = Result.retry()
        val studentId = inputData.keyValueMap[WorkerParams.STUDENT_ID.param] as String
        val flowStudentEntity = localDataSource.getStudentDetail(studentId)
        flowStudentEntity.collect {
            when(it) {
                is LocalAnswer.Success -> {
                    remoteDataSource.updateStudentProfile(
                        studentId, it.data.toStudentBody()
                    )
                    result = Result.success()
                }
                is LocalAnswer.Error -> Result.retry()
                is LocalAnswer.Empty -> Result.retry()
            }
        }
        return result
    }
}