package com.keeganboshoff.mobileapplication.ui.state

import com.keeganboshoff.mobileapplication.data.models.BetSessionResponse

data class HomeUiState(
    val fullName: String = "",
    val username: String = "",
    val sessions: List<BetSessionResponse> = emptyList(),
    val streak: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
