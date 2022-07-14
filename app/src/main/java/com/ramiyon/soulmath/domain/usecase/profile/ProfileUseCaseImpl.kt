package com.ramiyon.soulmath.domain.usecase.profile

import com.ramiyon.soulmath.data.source.dummy.getProfileAddOnsContent
import com.ramiyon.soulmath.domain.model.DailyXp
import com.ramiyon.soulmath.domain.model.Student
import com.ramiyon.soulmath.domain.repository.SoulMathRepository
import com.ramiyon.soulmath.util.ProfileAddOns
import com.ramiyon.soulmath.util.Resource
import kotlinx.coroutines.flow.Flow

class ProfileUseCaseImpl(
    private val repository: SoulMathRepository
) : ProfileUseCase {
    override fun getStudentDetail(): Flow<Resource<Student>> {
        return repository.getStudentDetail()
    }

    override fun getDailyXpList(): Flow<Resource<List<DailyXp>>> {
        return repository.getDailyXpList()
    }

    override fun getProfileAddOns(): List<Triple<Int, String, ProfileAddOns>> {
        return getProfileAddOnsContent()
    }

}
