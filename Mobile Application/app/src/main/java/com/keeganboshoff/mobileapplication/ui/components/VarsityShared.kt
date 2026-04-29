package com.keeganboshoff.mobileapplication.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.keeganboshoff.mobileapplication.ui.theme.ElectricMint
import com.keeganboshoff.mobileapplication.ui.theme.GlassBorder
import com.keeganboshoff.mobileapplication.ui.theme.GlassSurface
import com.keeganboshoff.mobileapplication.ui.theme.MidnightBlack
import com.keeganboshoff.mobileapplication.ui.theme.TextPrimary
import com.keeganboshoff.mobileapplication.ui.theme.TextSecondary
import kotlinx.coroutines.launch

@Composable
fun VarsityBranding(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "VARSITY BETS",
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            color = ElectricMint, //
            letterSpacing = 4.sp
        )
        Text(
            text = "Track your streak.",
            color = TextSecondary, //
            fontSize = 14.sp
        )
    }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(GlassSurface, RoundedCornerShape(28.dp)) //
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(listOf(GlassBorder, Color.Transparent)),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(24.dp)
    ) {
        Column(content = content)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VarsityInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    // For input better input focus when on-screen Keyboard is open.
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = TextSecondary) },
        leadingIcon = { Icon(icon, null, tint = ElectricMint) },
        modifier = Modifier
            .fillMaxWidth()
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusChanged {
                if (it.isFocused) {
                    scope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ElectricMint,
            unfocusedBorderColor = GlassBorder, //
            focusedContainerColor = MidnightBlack.copy(alpha = 0.4f),
            unfocusedContainerColor = MidnightBlack.copy(alpha = 0.4f),
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary
        ),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@Composable
fun VarsityButton(
    text: String,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ElectricMint,
            contentColor = MidnightBlack //
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MidnightBlack, modifier = Modifier.size(24.dp))
        } else {
            Text(text, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
        }
    }
}