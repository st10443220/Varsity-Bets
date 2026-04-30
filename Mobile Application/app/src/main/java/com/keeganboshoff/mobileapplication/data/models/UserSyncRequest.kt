package com.keeganboshoff.mobileapplication.data.models

data class UserSyncRequest(
    val firebaseUid: String,
    val username: String,
    val fullName: String
)