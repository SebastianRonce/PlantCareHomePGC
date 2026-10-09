package com.example.plantcarehomepgc.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcarehomepgc.data.model.User
import com.example.plantcarehomepgc.data.repository.AuthRepository
import com.example.plantcarehomepgc.data.repository.UserRepository
import com.example.plantcarehomepgc.utils.validateConfirmPassword
import com.example.plantcarehomepgc.utils.validateEmail
import com.example.plantcarehomepgc.utils.validateName
import com.example.plantcarehomepgc.utils.validatePassword
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    var name by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var confirmPassword by mutableStateOf("")
        private set

    var nameError by mutableStateOf("")
        private set
    var emailError by mutableStateOf("")
        private set
    var passwordError by mutableStateOf("")
        private set
    var confirmPasswordError by mutableStateOf("")
        private set
    var registerError by mutableStateOf("")
        private set
    var isLoading by mutableStateOf(false)
        private set
        
    var passwordVisible by mutableStateOf(false)
        private set
    var confirmPasswordVisible by mutableStateOf(false)
        private set

    var passwordStrength by mutableStateOf(0f)
        private set

    fun onNameChange(newValue: String) {
        name = newValue
        nameError = ""
    }

    fun onEmailChange(newValue: String) {
        email = newValue
        emailError = ""
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
        passwordError = ""
        calculatePasswordStrength(newValue)
    }

    private fun calculatePasswordStrength(pass: String) {
        var strength = 0f
        if (pass.length >= 8) strength += 0.25f
        if (pass.any { it.isDigit() }) strength += 0.25f
        if (pass.any { it.isLowerCase() } && pass.any { it.isUpperCase() }) strength += 0.25f
        val specialChars = "!@#$%^&*()_+-=[]{}|;':\",./<>?"
        if (pass.any { it in specialChars }) strength += 0.25f
        passwordStrength = strength
    }

    fun onConfirmPasswordChange(newValue: String) {
        confirmPassword = newValue
        confirmPasswordError = ""
    }
    
    fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }
    
    fun toggleConfirmPasswordVisibility() {
        confirmPasswordVisible = !confirmPasswordVisible
    }

    fun register(onSuccess: () -> Unit) {
        val nameVal = validateName(name)
        val emailVal = validateEmail(email)
        val passVal = validatePassword(password)
        val confirmVal = validateConfirmPassword(password, confirmPassword)

        nameError = nameVal.second
        emailError = emailVal.second
        passwordError = passVal.second
        confirmPasswordError = confirmVal.second

        if (nameVal.first && emailVal.first && passVal.first && confirmVal.first) {
            viewModelScope.launch {
                isLoading = true
                registerError = ""
                
                val authResult = authRepository.signUp(email.trim(), password)
                
                authResult.fold(
                    onSuccess = { firebaseUser ->
                        val newUser = User(
                            uid = firebaseUser.uid,
                            name = name.trim(),
                            email = email.trim(),
                            role = "user"
                        )
                        
                        val firestoreResult = userRepository.createUser(newUser)
                        
                        isLoading = false
                        firestoreResult.fold(
                            onSuccess = {
                                onSuccess()
                            },
                            onFailure = {
                                registerError = "Cuenta creada, pero hubo un error al guardar tu perfil."
                            }
                        )
                    },
                    onFailure = { exception ->
                        isLoading = false
                        registerError = when (exception) {
                            is FirebaseAuthUserCollisionException -> "Este correo ya está registrado."
                            is FirebaseAuthInvalidCredentialsException -> "El correo no es válido."
                            else -> "Ocurrió un error al crear la cuenta. Intenta de nuevo."
                        }
                    }
                )
            }
        }
    }
}
