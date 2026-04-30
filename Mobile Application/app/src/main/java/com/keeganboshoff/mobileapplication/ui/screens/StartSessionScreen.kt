package com.keeganboshoff.mobileapplication.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.keeganboshoff.mobileapplication.data.models.BetCategoryResponse
import com.keeganboshoff.mobileapplication.ui.components.GlassCard
import com.keeganboshoff.mobileapplication.ui.components.VarsityBranding
import com.keeganboshoff.mobileapplication.ui.components.VarsityButton
import com.keeganboshoff.mobileapplication.ui.components.VarsityInput
import com.keeganboshoff.mobileapplication.ui.theme.CyberViolet
import com.keeganboshoff.mobileapplication.ui.theme.DeepVioletGlow
import com.keeganboshoff.mobileapplication.ui.theme.GlassSurface
import com.keeganboshoff.mobileapplication.ui.theme.MidnightBlack
import com.keeganboshoff.mobileapplication.ui.theme.NeonRed
import com.keeganboshoff.mobileapplication.ui.theme.TextPrimary
import com.keeganboshoff.mobileapplication.ui.theme.TextSecondary
import com.keeganboshoff.mobileapplication.ui.viewmodel.StartSessionViewModel


@Composable
fun StartSessionScreen(
    onStartSessionSuccess: () -> Unit,
    viewModel: StartSessionViewModel = viewModel()
) {
    val uiState = viewModel.uiState
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(DeepVioletGlow, MidnightBlack)
                )
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
                )
            }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            VarsityBranding()
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "Select Category",
                color = TextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.categories) { category ->
                    CategoryChip(
                        category = category,
                        isSelected = uiState.selectedCategory?.id == category.id,
                        onClick = { viewModel.onCategorySelect(category) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            GlassCard {
                Column(modifier = Modifier.padding(16.dp)) {
                    VarsityInput(
                        value = uiState.buyIn,
                        onValueChange = { viewModel.onBuyInChange(it) },
                        label = "Starting Buy-In (R)",
                        icon = Icons.Default.Payments,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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

                    Spacer(modifier = Modifier.height(24.dp))

                    VarsityButton(
                        text = "START SESSION",
                        isLoading = uiState.isLoading,
                        onClick = {
                            focusManager.clearFocus()
                            viewModel.startSession(onSuccess = { onStartSessionSuccess() })
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryChip(category: BetCategoryResponse, isSelected: Boolean, onClick: () -> Unit) {
    val categoryColor = try {
        Color(category.hexColor.toColorInt())
    } catch (e: Exception) {
        CyberViolet
    }

    Surface(
        modifier = Modifier
            .width(110.dp)
            .height(130.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) categoryColor.copy(alpha = 0.2f) else GlassSurface,
        border = BorderStroke(2.dp, if (isSelected) categoryColor else Color.Transparent)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Casino,
                contentDescription = null,
                tint = if (isSelected) categoryColor else TextSecondary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                category.name,
                color = if (isSelected) TextPrimary else TextSecondary,
                fontSize = 14.sp
            )
        }
    }
}