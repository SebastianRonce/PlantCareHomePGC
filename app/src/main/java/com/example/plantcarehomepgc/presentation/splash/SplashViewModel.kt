package com.example.plantcarehomepgc.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcarehomepgc.data.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private val _startDestination = MutableStateFlow("login")
    val startDestination = _startDestination.asStateFlow()

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            // Tiempo mínimo de splash para mostrar la identidad visual
            delay(2000)
            if (repository.currentUser != null) {
                _startDestination.value = "home"
            } else {
                _startDestination.value = "login"
            }
            _isReady.value = true
        }
    }
}
