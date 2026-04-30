package com.keeganboshoff.mobileapplication.data.models

data class UserProfileResponse(
    val firebaseUid: String,
    val fullName: String,
    val username: String,
    val createdAt: String,
    val sessions: List<BetSessionResponse>
)
