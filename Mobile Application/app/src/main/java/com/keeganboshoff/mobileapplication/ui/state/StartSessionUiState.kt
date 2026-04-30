package com.keeganboshoff.mobileapplication.ui.state

import com.keeganboshoff.mobileapplication.data.models.BetCategoryResponse

data class StartSessionUiState(
    val isLoading: Boolean = false,
    val isStarting: Boolean = false,
    val categories: List<BetCategoryResponse> = emptyList(),
    val selectedCategory: BetCategoryResponse? = null,
    val buyIn: String = "",
    val errorMessage: String? = null
)