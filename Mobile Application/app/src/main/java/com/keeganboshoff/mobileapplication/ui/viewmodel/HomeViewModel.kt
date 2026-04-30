package com.keeganboshoff.mobileapplication.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.keeganboshoff.mobileapplication.data.models.BetSessionResponse
import com.keeganboshoff.mobileapplication.data.remote.RetrofitClient
import com.keeganboshoff.mobileapplication.ui.state.HomeUiState
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        fetchDashboardData()
    }

    private fun fetchDashboardData() {
        val user = FirebaseAuth.getInstance().currentUser
        uiState = uiState.copy(isLoading = true)

        user?.getIdToken(false)?.addOnSuccessListener { result ->
            val token = "Bearer ${result.token}"

            viewModelScope.launch {
                try {

                    val userResponse = RetrofitClient.apiService.getUserProfile(
                        token = token
                    )

                    val sessionsResponse = RetrofitClient.apiService.getUserHistory(
                        token = token,
                        uid = user.uid
                    )

                    if (userResponse.isSuccessful && sessionsResponse.isSuccessful) {
                        val userProfile = userResponse.body()
                        val sessionsList = sessionsResponse.body() ?: emptyList()

                        uiState = uiState.copy(
                            fullName = userProfile?.fullName ?: "User",
                            username = userProfile?.username ?: "",
                            streak = calculateWinStreak(sessionsList),
                            sessions = sessionsList,
                            isLoading = false
                        )
                    } else {
                        uiState = uiState.copy(
                            isLoading = false,
                            errorMessage = "Error: ${userResponse.code()}"
                        )
                    }
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = "Network error: Could not reach the API}"
                    )
                }
            }
        }
    }

    private fun calculateWinStreak(sessions: List<BetSessionResponse>): Int {
        if (sessions.isEmpty()) return 0

        val sorted = sessions.sortedByDescending { it.date }

        var streak = 0

        for (session in sorted) {
            if (session.profit > 0) {
                streak++
            } else {
                break
            }
        }

        return streak
    }
}