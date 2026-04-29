package com.keeganboshoff.mobileapplication.data.models

data class UserSyncRequest(
    val uid: String,
    val email: String,
    val username: String,
    val fullName: String
)