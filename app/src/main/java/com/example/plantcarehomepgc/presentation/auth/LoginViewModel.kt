package com.example.plantcarehomepgc.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcarehomepgc.data.repository.AuthRepository
import com.example.plantcarehomepgc.utils.validateEmail
import com.example.plantcarehomepgc.utils.validatePassword
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    var emailError by mutableStateOf("")
        private set
    var passwordError by mutableStateOf("")
        private set
    var loginError by mutableStateOf("")
        private set
    var isLoading by mutableStateOf(false)
        private set
    var passwordVisible by mutableStateOf(false)
        private set

    fun onEmailChange(newValue: String) {
        email = newValue
        emailError = ""
        loginError = ""
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
        passwordError = ""
        loginError = ""
    }
    
    fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    fun login(onSuccess: () -> Unit) {
        val emailVal = validateEmail(email)
        val passwordVal = validatePassword(password)

        emailError = emailVal.second
        passwordError = passwordVal.second

        if (emailVal.first && passwordVal.first) {
            viewModelScope.launch {
                isLoading = true
                loginError = ""
                val result = repository.signIn(email.trim(), password)
                isLoading = false
                
                result.fold(
                    onSuccess = {
                        onSuccess()
                    },
                    onFailure = { exception ->
                        loginError = when (exception) {
                            is FirebaseAuthInvalidCredentialsException -> "Correo o contraseña incorrectos."
                            is FirebaseAuthInvalidUserException -> "Esta cuenta no existe."
                            else -> "Error de conexión. Inténtalo de nuevo."
                        }
                    }
                )
            }
        }
    }

    fun resetPassword() {
        val validation = validateEmail(email)
        if (validation.first) {
            viewModelScope.launch {
                isLoading = true
                val result = repository.sendPasswordReset(email.trim())
                isLoading = false
                result.fold(
                    onSuccess = {
                        loginError = "Correo de recuperación enviado exitosamente."
                    },
                    onFailure = {
                        loginError = "No se pudo enviar el correo. Verifica tu conexión."
                    }
                )
            }
        } else {
            emailError = "Ingresa tu email para recuperar la contraseña."
        }
    }
}
