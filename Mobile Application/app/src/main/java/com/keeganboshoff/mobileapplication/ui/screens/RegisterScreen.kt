package com.keeganboshoff.mobileapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.keeganboshoff.mobileapplication.ui.components.GlassCard
import com.keeganboshoff.mobileapplication.ui.components.VarsityBranding
import com.keeganboshoff.mobileapplication.ui.components.VarsityButton
import com.keeganboshoff.mobileapplication.ui.components.VarsityInput
import com.keeganboshoff.mobileapplication.ui.theme.CyberViolet
import com.keeganboshoff.mobileapplication.ui.theme.DeepVioletGlow
import com.keeganboshoff.mobileapplication.ui.theme.MidnightBlack
import com.keeganboshoff.mobileapplication.ui.theme.NeonRed
import com.keeganboshoff.mobileapplication.ui.theme.TextPrimary
import com.keeganboshoff.mobileapplication.ui.theme.TextSecondary
import com.keeganboshoff.mobileapplication.ui.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    // State Management
    val uiState = viewModel.registerUiState

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                verticalGradient(
                    listOf(
                        DeepVioletGlow, MidnightBlack
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            VarsityBranding()

            Spacer(
                modifier = Modifier
                    .height(32.dp)
            )

            // Create Account
            GlassCard {
                Text(
                    "Create Account",
                    color = TextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // Name
                VarsityInput(
                    uiState.fullName,
                    { viewModel.onFullNameChange(it) },
                    "Full Name",
                    Icons.Outlined.Badge
                )

                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )

                // Email
                VarsityInput(
                    uiState.email,
                    { viewModel.onEmailChange(it) },
                    "Email Address",
                    Icons.Outlined.Email
                )

                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )

                // Username
                VarsityInput(
                    uiState.username,
                    { viewModel.onUsernameChange(it) },
                    "Username",
                    Icons.Outlined.Person
                )

                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )

                // Password
                VarsityInput(
                    uiState.password,
                    { viewModel.onPasswordChange(it) },
                    "Password",
                    Icons.Outlined.Lock,
                    true
                )

                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )

                // Confirm Password
                VarsityInput(
                    uiState.confirmPassword,
                    { viewModel.onConfirmPasswordChange(it) },
                    "Confirm Password",
                    Icons.Outlined.CheckCircle,
                    true
                )

                // Display the error message
                uiState.errorMessage?.let { error ->
                    Text(
                        text = error,
                        color = NeonRed,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(
                                top = 8.dp
                            )
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(32.dp)
                )

                // Register Button
                VarsityButton(
                    text = "REGISTER",
                    isLoading = uiState.isLoading
                ) {
                    viewModel.registerUser(onSuccess = { onNavigateBack() })
                }
            }

            Spacer(
                modifier = Modifier
                    .height(24.dp)
            )

            // Login Screen
            Row {
                Text(
                    "Already have an account? ",
                    color = TextSecondary
                )
                Text(
                    "Login",
                    color = CyberViolet,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable {
                            onNavigateBack()
                        })
            }
        }
    }
}