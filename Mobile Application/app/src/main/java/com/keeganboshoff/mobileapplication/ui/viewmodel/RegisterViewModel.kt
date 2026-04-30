package com.keeganboshoff.mobileapplication.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.keeganboshoff.mobileapplication.data.models.UserSyncRequest
import com.keeganboshoff.mobileapplication.data.remote.RetrofitClient
import com.keeganboshoff.mobileapplication.data.service.AccountService
import com.keeganboshoff.mobileapplication.data.service.implementation.AccountServiceImplementation
import com.keeganboshoff.mobileapplication.ui.state.RegisterUiState
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val accountService: AccountService = AccountServiceImplementation()
) :
    ViewModel() {
    var registerUiState by mutableStateOf(RegisterUiState())
        private set

    fun onFullNameChange(newValue: String) {
        registerUiState = registerUiState.copy(fullName = newValue)
    }

    fun onEmailChange(newValue: String) {
        registerUiState = registerUiState.copy(email = newValue)
    }

    fun onUsernameChange(newValue: String) {
        registerUiState = registerUiState.copy(username = newValue)
    }

    fun onPasswordChange(newValue: String) {
        registerUiState = registerUiState.copy(password = newValue)
    }

    fun onConfirmPasswordChange(newValue: String) {
        registerUiState = registerUiState.copy(confirmPassword = newValue)
    }

    fun registerUser(onSuccess: (String) -> Unit) {
        val state = registerUiState

        // Check all fields are filled in
        if (state.fullName.isBlank()
            || state.email.isBlank()
            || state.username.isBlank()
            || state.password.isBlank()
            || state.confirmPassword.isBlank()
        ) {
            registerUiState = state.copy(errorMessage = "Please fill in all fields.")
            return
        }

        // Check if the email is valid
        if (!state.email.contains(char = '@')
            || (state.email.lastIndexOf(char = '.') < state.email.indexOf(char = '@'))
        ) {
            registerUiState = state.copy(errorMessage = "Please enter a valid email.")
            return
        }

        // Check if the username length is between 5 and 15 characters long
        if (state.username.length < 5 || state.username.length > 15) {
            registerUiState =
                state.copy(errorMessage = "Username must be between 5 and 15 characters long.")
            return
        }

        // Check if the username does not contain any special characters
        if (state.username.any { !it.isLetterOrDigit() }) {
            registerUiState =
                state.copy(errorMessage = "Username must not contain special characters.")
            return
        }

        // Check if the password is 8 or more in length
        if (state.password.length < 8) {
            registerUiState =
                state.copy(errorMessage = "Password must be 8 or more characters long.")
            return
        }

        // Check if the password contains an uppercase
        if (!state.password.any { it.isUpperCase() }) {
            registerUiState =
                state.copy(errorMessage = "Password must contain an uppercase.")
            return
        }

        // Check if the password contains a special character
        if (!state.password.any { !it.isLetterOrDigit() }) {
            registerUiState =
                state.copy(errorMessage = "Password must contain a special character.")
            return
        }

        // Check if passwords are the same
        if (state.password != state.confirmPassword) {
            registerUiState = state.copy(errorMessage = "Passwords do not match.")
            return
        }

        registerUiState = state.copy(isLoading = true, errorMessage = null)

        // Try to create the account
        accountService.createAccount(
            email = state.email,
            password = state.password
        ) { error ->
            if (error == null) {
                // Success
                // Get created users firebase uid
                val user = FirebaseAuth.getInstance().currentUser

                // Ask firebase for the latest token
                user?.getIdToken(false)?.addOnSuccessListener { result ->
                    val token = "Bearer ${result.token}"

                    // Start coroutine to sync user data with API service
                    viewModelScope.launch {
                        // Try sync user data
                        try {
                            val response = RetrofitClient.apiService.syncUser(
                                token = token,
                                request = UserSyncRequest(
                                    firebaseUid = user.uid,
                                    username = state.username,
                                    fullName = state.fullName
                                )
                            )

                            // Response Sync Success
                            if (response.isSuccessful) {
                                registerUiState = registerUiState.copy(isLoading = false)
                                onSuccess(state.fullName)
                            } else {
                                // Response Sync Fail
                                registerUiState = registerUiState.copy(
                                    isLoading = false,
                                    errorMessage = "Database Sync Failed (Code: ${response.code()})"
                                )
                            }
                            // Could not sync user data
                        } catch (e: Exception) {
                            registerUiState = registerUiState.copy(
                                isLoading = false,
                                errorMessage = "Network error: Could not reach the API"
                            )
                        }
                    }
                }


            } else {
                // Failure
                // Update Ui to state with firebase error
                registerUiState = state.copy(
                    isLoading = false,
                    errorMessage = error.localizedMessage ?: "Registration failed."
                )
            }
        }
    }
}