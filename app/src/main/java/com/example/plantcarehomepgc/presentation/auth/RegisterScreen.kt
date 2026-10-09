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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onClickBack: () -> Unit = {},
    onSuccessfulRegister: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Cuenta", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onClickBack, enabled = !viewModel.isLoading) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Image(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Únete a Plant Care Home",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Completa tus datos para comenzar a cuidar tus plantas",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )

            // NOMBRE
            OutlinedTextField(
                value = viewModel.name,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                isError = viewModel.nameError.isNotEmpty(),
                supportingText = { if (viewModel.nameError.isNotEmpty()) Text(viewModel.nameError) },
                singleLine = true,
                enabled = !viewModel.isLoading,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )

            Spacer(modifier = Modifier.height(8.dp))

            // CORREO
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                isError = viewModel.emailError.isNotEmpty(),
                supportingText = { if (viewModel.emailError.isNotEmpty()) Text(viewModel.emailError) },
                singleLine = true,
                enabled = !viewModel.isLoading,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )

            Spacer(modifier = Modifier.height(8.dp))

            // CONTRASEÑA
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                trailingIcon = {
                    val image = if (viewModel.passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                isError = viewModel.passwordError.isNotEmpty(),
                supportingText = { 
                    if (viewModel.passwordError.isNotEmpty()) {
                        Text(viewModel.passwordError)
                    } else {
                        Text("Mín. 8 caracteres, un número y un símbolo", fontSize = 11.sp)
                    }
                },
                visualTransformation = if (viewModel.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                enabled = !viewModel.isLoading,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )

            Spacer(modifier = Modifier.height(8.dp))

            // CONFIRMAR CONTRASEÑA
            OutlinedTextField(
                value = viewModel.confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordChange(it) },
                label = { Text("Confirmar Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                trailingIcon = {
                    val image = if (viewModel.confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { viewModel.toggleConfirmPasswordVisibility() }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                isError = viewModel.confirmPasswordError.isNotEmpty(),
                supportingText = { if (viewModel.confirmPasswordError.isNotEmpty()) Text(viewModel.confirmPasswordError) },
                visualTransformation = if (viewModel.confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                enabled = !viewModel.isLoading,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )

            if (viewModel.registerError.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = viewModel.registerError,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // BOTON REGISTRO
            Button(
                onClick = {
                    viewModel.register(onSuccessfulRegister)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                enabled = !viewModel.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White, strokeWidth = 2.dp)
                } else {
                    Text("Registrarse", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
