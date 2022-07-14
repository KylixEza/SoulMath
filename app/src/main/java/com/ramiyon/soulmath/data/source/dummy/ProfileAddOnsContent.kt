package com.ramiyon.soulmath.data.source.dummy

import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.util.ProfileAddOns

fun getProfileAddOnsContent() = listOf(
    Triple(R.drawable.ic_profile_add_on, "Ubah Profil", ProfileAddOns.PROFILE),
    Triple(R.drawable.ic_favorite_add_on, "Materi Favorit", ProfileAddOns.FAVORITE),
    Triple(R.drawable.ic_password_changed_add_on, "Ubah Kata Sandi", ProfileAddOns.PASSWORD),
    Triple(R.drawable.ic_contact_us_add_on, "Contact Us", ProfileAddOns.CONTACT),
    Triple(R.drawable.ic_terms_and_privacy_policy_add_on, "Syarat & Kebijakan Privasi", ProfileAddOns.TERMS),
    Triple(R.drawable.ic_logout_add_on, "Log Out", ProfileAddOns.LOGOUT)
)