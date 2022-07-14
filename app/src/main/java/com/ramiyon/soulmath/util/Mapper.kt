package com.ramiyon.soulmath.util

import com.ramiyon.soulmath.data.source.local.database.enitity.DailyXpEntity
import com.ramiyon.soulmath.data.source.local.database.enitity.StudentEntity
import com.ramiyon.soulmath.data.source.remote.api.response.leaderboard.LeaderboardResponse
import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentBody
import com.ramiyon.soulmath.data.source.remote.api.response.student.StudentResponse
import com.ramiyon.soulmath.domain.model.DailyXp
import com.ramiyon.soulmath.domain.model.Leaderboard
import com.ramiyon.soulmath.domain.model.Student

fun Student.toStudentBody() = StudentBody(
    studentId, address, avatar, username, email, phoneNumber, xp
)

fun Student.toStudentEntity() = StudentEntity(
    studentId, address, avatar, username, email, phoneNumber, xp
)

fun StudentResponse.toStudentEntity() = StudentEntity(
    studentId, address, avatar, username, email, phoneNumber, xp
)

fun StudentResponse.toStudent() = Student(
    studentId, address, avatar, username, email, phoneNumber, xp
)

fun StudentEntity.toStudentBody() = StudentBody(
    studentId, address, avatar, username, email, phoneNumber, xp
)

fun StudentEntity.toStudent() = Student(
    studentId, address, avatar, username, email, phoneNumber, xp
)

fun LeaderboardResponse.toLeaderboard() = Leaderboard(
    username, avatar, xp, rank
)

fun DailyXpEntity.toDailyXp() = DailyXp(
    dailyXpId, dailyXp, day, isTaken
)