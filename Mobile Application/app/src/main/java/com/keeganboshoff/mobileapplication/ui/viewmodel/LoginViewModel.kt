package com.keeganboshoff.mobileapplication.ui.viewmodel

//import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.keeganboshoff.mobileapplication.data.service.AccountService
import com.keeganboshoff.mobileapplication.data.service.implementation.AccountServiceImplementation
import com.keeganboshoff.mobileapplication.ui.state.AuthUiState

class AuthViewModel(
    private val accountService: AccountService = AccountServiceImplementation()
) : ViewModel() {

    var authUiState by mutableStateOf(AuthUiState())
        private set

    fun onEmailChange(newValue: String) {
        authUiState = authUiState.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        authUiState = authUiState.copy(password = newValue)
    }

    fun loginUser(onSuccess: () -> Unit) {
        val state = authUiState

        // Check all fields are filled in
        if (state.email.isBlank()
            || state.password.isBlank()
        ) {
            authUiState = state.copy(errorMessage = "Please enter your credentials.")
            return
        }

        // Check if the email is valid
        if (!state.email.contains(char = '@')
            && (state.email.lastIndexOf(char = '.') < state.email.indexOf(char = '@'))
        ) {
            authUiState = state.copy(errorMessage = "Please enter a valid email.")
            return
        }

        authUiState = state.copy(isLoading = true, errorMessage = null)

        // Try login to the account
        accountService.authenticate(
            email = state.email,
            password = state.password
        ) { error ->
            if (error == null) {
                // Success
                authUiState = state.copy(isLoading = false)
                onSuccess()
            } else {
                // Failure
                // Update Ui with the firebase error
                authUiState = state.copy(
                    isLoading = false,
                    errorMessage = error.localizedMessage ?: "Incorrect login details."
                )
            }
        }
    }
}