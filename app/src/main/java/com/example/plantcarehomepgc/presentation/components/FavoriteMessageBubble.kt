package com.example.plantcarehomepgc.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun FavoriteMessageBubble(
    message: String?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    durationMillis: Long = 2800L
) {
    var lastMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(message) {
        if (message != null) {
            lastMessage = message
            delay(durationMillis)
            onDismiss()
        } else {
            delay(300)
            lastMessage = null
        }
    }

    AnimatedVisibility(
        visible = message != null,
        enter = fadeIn(animationSpec = tween(300)) + slideInVertically(
            animationSpec = tween(300),
            initialOffsetY = { it / 2 }
        ),
        exit = fadeOut(animationSpec = tween(300)) + slideOutVertically(
            animationSpec = tween(300),
            targetOffsetY = { it / 2 }
        ),
        modifier = modifier
    ) {
        Surface(
            shape = CircleShape,
            color = Color(0xFF454545),
            contentColor = Color.White,
            shadowElevation = 8.dp,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .widthIn(max = 340.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = lastMessage ?: message ?: "",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
            }
        }
    }
}
