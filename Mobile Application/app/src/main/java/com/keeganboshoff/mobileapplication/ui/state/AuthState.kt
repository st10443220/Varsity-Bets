package com.keeganboshoff.mobileapplication.ui.state

data class AuthState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)
