package com.ramiyon.soulmath.util

import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentBody
import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentResponse
import com.ramiyon.soulmath.domain.model.Student

fun Student.toStudentBody() = StudentBody(
    studentId, address, avatar, username, email, phoneNumber, xp
)

fun StudentResponse.toStudent() = Student(
    studentId, address, avatar, username, email, phoneNumber, xp
)
