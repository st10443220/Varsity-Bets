package com.keeganboshoff.mobileapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.keeganboshoff.mobileapplication.ui.theme.MidnightBlack
import com.keeganboshoff.mobileapplication.ui.theme.TextPrimary

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightBlack),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Welcome to the Dashboard!",
            color = TextPrimary,
            fontSize = 24.sp
        )
    }
}