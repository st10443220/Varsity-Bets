package com.keeganboshoff.mobileapplication.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.keeganboshoff.mobileapplication.data.models.BetCategoryResponse
import com.keeganboshoff.mobileapplication.data.models.StartSessionRequest
import com.keeganboshoff.mobileapplication.data.remote.RetrofitClient
import com.keeganboshoff.mobileapplication.ui.state.StartSessionUiState
import kotlinx.coroutines.launch

class StartSessionViewModel : ViewModel() {
    var uiState by mutableStateOf(StartSessionUiState())
        private set

    init {
        loadCategories()
    }

    private fun loadCategories() {
        uiState = uiState.copy(isLoading = true)
        val user = FirebaseAuth.getInstance().currentUser
        user?.getIdToken(false)?.addOnSuccessListener { result ->
            val token = "Bearer ${result.token}"
            viewModelScope.launch {
                try {
                    val response = RetrofitClient.apiService.getCategories(token)
                    if (response.isSuccessful) {
                        val cats = response.body() ?: emptyList()
                        uiState = uiState.copy(
                            categories = cats,
                            selectedCategory = cats.firstOrNull(),
                            isLoading = false
                        )
                    }
                } catch (e: Exception) {
                    uiState =
                        uiState.copy(isLoading = false, errorMessage = "Failed to load categories")
                }
            }
        }
    }

    fun onBuyInChange(newValue: String) {
        uiState = uiState.copy(buyIn = newValue, errorMessage = null)
    }

    fun onCategorySelect(category: BetCategoryResponse) {
        uiState = uiState.copy(selectedCategory = category)
    }

    fun startSession(onSuccess: () -> Unit) {
        val amount = uiState.buyIn.toDoubleOrNull() ?: 0.0
        val categoryId = uiState.selectedCategory?.id ?: return

        if (amount <= 0) {
            uiState = uiState.copy(errorMessage = "Please enter a valid buy-in.")
            return
        }

        uiState = uiState.copy(isStarting = true)
        val user = FirebaseAuth.getInstance().currentUser
        user?.getIdToken(false)?.addOnSuccessListener { result ->
            val token = "Bearer ${result.token}"
            viewModelScope.launch {
                try {
                    val request = StartSessionRequest(
                        buyInAmount = amount,
                        betCategoryId = categoryId
                    )
                    val response = RetrofitClient.apiService.startSession(
                        token = token,
                        session = request
                    )

                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        val serverError =
                            response.errorBody()?.string() ?: "Error code: ${response.code()}"
                        uiState = uiState.copy(
                            isStarting = false,
                            errorMessage = serverError
                        )
                    }
                } catch (e: Exception) {
                    uiState = uiState.copy(isStarting = false, errorMessage = "Network Error")
                }
            }
        }
    }
}