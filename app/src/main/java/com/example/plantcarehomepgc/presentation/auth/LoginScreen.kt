package com.example.plantcarehomepgc.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onClickRegister: () -> Unit = {},
    onSuccessfulLogin: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Logo Plant Care Home",
                modifier = Modifier.size(150.dp)
            )
            
            Text(
                text = "Iniciar Sesión",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Campo de Email
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Email") },
                placeholder = { Text("ejemplo@correo.com") },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                isError = viewModel.emailError.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    if (viewModel.emailError.isNotEmpty()) {
                        Text(text = viewModel.emailError, color = MaterialTheme.colorScheme.error)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true,
                enabled = !viewModel.isLoading
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Campo de Contraseña
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                trailingIcon = {
                    val image = if (viewModel.passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                isError = viewModel.passwordError.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    if (viewModel.passwordError.isNotEmpty()) {
                        Text(text = viewModel.passwordError, color = MaterialTheme.colorScheme.error)
                    }
                },
                visualTransformation = if (viewModel.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                singleLine = true,
                enabled = !viewModel.isLoading
            )

            if (viewModel.loginError.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                val isSuccessMessage = viewModel.loginError.contains("enviado")
                Text(
                    text = viewModel.loginError,
                    color = if (isSuccessMessage) Color(0xFF4CAF50) else MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón Iniciar Sesión
            Button(
                onClick = {
                    viewModel.login(onSuccessfulLogin)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !viewModel.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Iniciar Sesión", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Recuperación de contraseña
            TextButton(
                onClick = { viewModel.resetPassword() },
                enabled = !viewModel.isLoading
            ) {
                Text("¿Olvidaste tu contraseña?", color = Color.Black)
            }

            // Registro
            TextButton(
                onClick = onClickRegister,
                enabled = !viewModel.isLoading
            ) {
                Text("¿No tienes una cuenta? Regístrate", color = Color.Black)
            }
        }
    }
}
