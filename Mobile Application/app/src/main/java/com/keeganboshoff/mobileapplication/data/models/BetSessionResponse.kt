package com.keeganboshoff.mobileapplication.data.models

data class BetSessionResponse(
    val id: Int,
    val categoryName: String,
    val categoryIcon: String,
    val buyIn: Double,
    val cashOut: Double,
    val profit: Double,
    val date: String,
    val isActive: Boolean
)