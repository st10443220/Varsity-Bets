package com.keeganboshoff.mobileapplication.ui.state

data class HomeUiState(
    val fullName: String = "",
    val username: String = "",
    val sessionsCount: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
