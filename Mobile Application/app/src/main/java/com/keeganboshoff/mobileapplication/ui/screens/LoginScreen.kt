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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
import com.keeganboshoff.mobileapplication.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    // UI State Management
    val uiState = viewModel.loginUiState
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(DeepVioletGlow, MidnightBlack)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Directly using the shared Branding component
            VarsityBranding()

            Spacer(modifier = Modifier.height(32.dp))

            // Directly using the shared GlassCard
            GlassCard {
                Text(
                    text = "Login",
                    color = TextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // Shared Input Component for Username/Email
                VarsityInput(
                    value = uiState.email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    label = "Username or Email",
                    icon = Icons.Outlined.Person,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Shared Input Component for Password
                VarsityInput(
                    value = uiState.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = "Password",
                    icon = Icons.Outlined.Lock,
                    isPassword = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            viewModel.loginUser(onSuccess = { onLoginSuccess() })
                        }
                    )
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

                Spacer(modifier = Modifier.height(32.dp))

                // Shared Button Component
                VarsityButton(
                    text = "LOG IN",
                    isLoading = uiState.isLoading,
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.loginUser(onSuccess = { onLoginSuccess() })
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Register Screen
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "New here? ",
                    color = TextSecondary
                )
                Text(
                    text = "Create Account",
                    color = CyberViolet,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToRegister() }
                )
            }
        }
    }
}