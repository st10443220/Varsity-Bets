package com.keeganboshoff.mobileapplication.data.models

data class BetSessionResponse(
    val id: Int,
    val userProfileFirebaseUid: String,
    val betCategoryId: Int,
    val buyInAmount: Double,
    val cashOutAmount: Double,
    val startTime: String,
    val endTime: String?
) {
    val profit: Double
        get() = cashOutAmount - buyInAmount

    val isActive: Boolean
        get() = endTime == null
}