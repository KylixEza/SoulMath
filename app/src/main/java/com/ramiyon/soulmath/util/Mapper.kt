package com.ramiyon.soulmath.util

import com.ramiyon.soulmath.data.source.remote.api.response.UserBody
import com.ramiyon.soulmath.domain.model.User

fun User.toUserBody() = UserBody(
    uid, address, avatar, email, name, phoneNumber,
)
