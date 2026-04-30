package com.keeganboshoff.mobileapplication.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.keeganboshoff.mobileapplication.data.remote.RetrofitClient
import com.keeganboshoff.mobileapplication.ui.state.HomeUiState
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        val user = FirebaseAuth.getInstance().currentUser
        uiState = uiState.copy(isLoading = true)

        user?.getIdToken(false)?.addOnSuccessListener { result ->
            val token = "Bearer ${result.token}"

            viewModelScope.launch {
                try {
                    val response = RetrofitClient.apiService.getUserProfile(
                        token = token
                    )

                    if (response.isSuccessful) {
                        val userProfile = response.body()

                        uiState = uiState.copy(
                            fullName = userProfile?.fullName ?: "User",
                            username = userProfile?.username ?: "default user",
                            sessionsCount = userProfile?.sessions?.size ?: 0,
                            isLoading = false
                        )
                    } else {
                        uiState = uiState.copy(
                            isLoading = false,
                            errorMessage = "Error: ${response.code()}"
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
}