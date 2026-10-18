package com.example.plantcarehomepgc.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantcarehomepgc.data.repository.AuthRepository
import com.example.plantcarehomepgc.data.repository.FavoriteRepository
import com.example.plantcarehomepgc.data.repository.PlantRepository
import com.example.plantcarehomepgc.data.repository.UserRepository
import com.example.plantcarehomepgc.presentation.home.HomeViewModel
import com.example.plantcarehomepgc.presentation.plants.PlantDetailViewModel
import com.example.plantcarehomepgc.presentation.profile.ProfileViewModel
import com.example.plantcarehomepgc.presentation.splash.SplashViewModel

class AuthViewModelFactory(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val plantRepository: PlantRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                SplashViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                LoginViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                RegisterViewModel(authRepository, userRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                ProfileViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                HomeViewModel(plantRepository, favoriteRepository, authRepository) as T
            }
            modelClass.isAssignableFrom(PlantDetailViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                PlantDetailViewModel(plantRepository, favoriteRepository, authRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
