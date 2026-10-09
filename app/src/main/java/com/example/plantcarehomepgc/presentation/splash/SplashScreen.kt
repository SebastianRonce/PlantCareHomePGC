package com.example.plantcarehomepgc.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Yard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    onNavigate: (String) -> Unit
) {
    val isReady by viewModel.isReady.collectAsState()
    val startDestination by viewModel.startDestination.collectAsState()

    LaunchedEffect(isReady) {
        if (isReady) {
            onNavigate(startDestination)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Yard,
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Plant Care Home",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}
