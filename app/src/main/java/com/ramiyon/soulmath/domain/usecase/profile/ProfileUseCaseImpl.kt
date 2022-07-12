package com.ramiyon.soulmath.domain.usecase.profile

import com.ramiyon.soulmath.domain.model.Student
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

class ProfileUseCaseImpl(
    private val repository: SoulMathRepository
) : ProfileUseCase {
    override fun getStudentDetail(): Flow<Resource<Student>> {
        return repository.getStudentDetail()
    }

}
