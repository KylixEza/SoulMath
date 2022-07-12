package com.ramiyon.soulmath.domain.usecase.profile

import com.ramiyon.soulmath.domain.model.Student
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileUseCase {

    fun getStudentDetail(): Flow<Resource<Student>>

}