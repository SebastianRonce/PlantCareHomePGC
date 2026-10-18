package com.example.plantcarehomepgc.presentation.plants

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcarehomepgc.data.model.Plant
import com.example.plantcarehomepgc.data.repository.AuthRepository
import com.example.plantcarehomepgc.data.repository.FavoriteRepository
import com.example.plantcarehomepgc.data.repository.PlantRepository
import kotlinx.coroutines.launch

class PlantDetailViewModel(
    private val repository: PlantRepository,
    private val favoriteRepository: FavoriteRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    var plant by mutableStateOf<Plant?>(null)
        private set

    var currentPlantId by mutableStateOf("")
        private set

    var isFavorite by mutableStateOf(false)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var userMessage by mutableStateOf<String?>(null)
        private set

    fun clearError() {
        errorMessage = null
    }

    fun userMessageShown() {
        userMessage = null
    }

    fun loadPlant(plantId: String) {
        if (plantId.isBlank()) {
            errorMessage = "ID de planta no válido"
            return
        }
        currentPlantId = plantId
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            repository.getPlantById(plantId).fold(
                onSuccess = { fetchedPlant ->
                    plant = fetchedPlant
                    checkFavoriteStatus(plantId)
                },
                onFailure = { e ->
                    Log.e("PlantDetailViewModel", "Error al cargar la planta $plantId", e)
                    errorMessage = "Error al cargar los detalles de la planta" 
                }
            )
            isLoading = false
        }
    }

    fun checkFavoriteStatus(plantId: String) {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            val fav = favoriteRepository.isFavorite(uid, plantId)
            isFavorite = fav
        }
    }

    fun toggleFavorite() {
        val targetId = plant?.id?.ifBlank { currentPlantId } ?: currentPlantId
        if (targetId.isBlank()) {
            userMessage = "No se puede guardar favorito: ID de planta no encontrado."
            return
        }

        val uid = authRepository.currentUser?.uid
        if (uid == null) {
            userMessage = "Debes iniciar sesión para guardar favoritos"
            return
        }

        val previousState = isFavorite
        val newState = !previousState
        isFavorite = newState // Actualización optimista de UI

        viewModelScope.launch {
            val result = if (previousState) {
                favoriteRepository.removeFavorite(uid, targetId)
            } else {
                favoriteRepository.addFavorite(uid, targetId)
            }

            result.fold(
                onSuccess = {
                    userMessage = if (previousState) {
                        "Planta eliminada de tus favoritos"
                    } else {
                        "¡Planta guardada en tus favoritos!"
                    }
                },
                onFailure = { e ->
                    Log.e("PlantDetailViewModel", "Error al modificar favorito en Firestore para $targetId", e)
                    isFavorite = previousState // Revertir en caso de falla
                    userMessage = "No se pudo actualizar tus favoritos. Inténtalo de nuevo"
                }
            )
        }
    }
}
