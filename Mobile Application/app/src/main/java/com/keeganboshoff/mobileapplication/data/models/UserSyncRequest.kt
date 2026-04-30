package com.keeganboshoff.mobileapplication.data.models

data class UserSyncRequest(
    val firebaseUid: String,
    val fullName: String,
    val username: String
)