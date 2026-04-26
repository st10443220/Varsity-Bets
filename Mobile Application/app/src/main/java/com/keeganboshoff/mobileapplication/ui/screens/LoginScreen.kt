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
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    // UI States hoisted to the top level
    var identifier by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

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
                    value = identifier,
                    onValueChange = { identifier = it },
                    label = "Username or Email",
                    icon = Icons.Outlined.Person
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Shared Input Component for Password
                VarsityInput(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    icon = Icons.Outlined.Lock,
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Shared Button Component
                VarsityButton(
                    text = "LOG IN",
                    isLoading = isLoading,
                    onClick = {
                        isLoading = true
                        // Firebase logic will live here later
                        onLoginSuccess()
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