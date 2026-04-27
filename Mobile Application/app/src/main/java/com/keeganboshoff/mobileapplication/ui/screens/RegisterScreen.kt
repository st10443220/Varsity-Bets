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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.keeganboshoff.mobileapplication.ui.components.GlassCard
import com.keeganboshoff.mobileapplication.ui.components.VarsityBranding
import com.keeganboshoff.mobileapplication.ui.components.VarsityButton
import com.keeganboshoff.mobileapplication.ui.components.VarsityInput
import com.keeganboshoff.mobileapplication.ui.theme.CyberViolet
import com.keeganboshoff.mobileapplication.ui.theme.DeepVioletGlow
import com.keeganboshoff.mobileapplication.ui.theme.MidnightBlack
import com.keeganboshoff.mobileapplication.ui.theme.TextPrimary
import com.keeganboshoff.mobileapplication.ui.theme.TextSecondary

@Composable
fun RegisterScreen(onNavigateBack: () -> Unit) {
    // State Management
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
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
                    fullName,
                    { fullName = it },
                    "Full Name",
                    Icons.Outlined.Badge
                )

                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )

                // Email
                VarsityInput(
                    email,
                    { email = it },
                    "Email Address",
                    Icons.Outlined.Email
                )

                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )

                // Username
                VarsityInput(
                    username,
                    { username = it },
                    "Username",
                    Icons.Outlined.Person
                )

                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )

                // Password
                VarsityInput(
                    password,
                    { password = it },
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
                    confirmPassword,
                    { confirmPassword = it },
                    "Confirm Password",
                    Icons.Outlined.CheckCircle,
                    true
                )

                Spacer(
                    modifier = Modifier
                        .height(32.dp)
                )

                // Register Button
                VarsityButton("REGISTER") {
                    isLoading = true
                    // TODO: Firebase Registration logic
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