package com.example.plantcarehomepgc.presentation.home

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

class HomeViewModel(
    private val repository: PlantRepository,
    private val favoriteRepository: FavoriteRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    
    var searchText by mutableStateOf("")
        private set

    var plants by mutableStateOf<List<Plant>>(emptyList())
        private set

    var favoritePlantIds by mutableStateOf<Set<String>>(emptySet())
        private set

    var isFavoritesFilterActive by mutableStateOf(false)
        private set
        
    var isLoading by mutableStateOf(false)
        private set
        
    var errorMessage by mutableStateOf<String?>(null)
        private set

    val displayedPlants: List<Plant>
        get() = if (isFavoritesFilterActive) {
            plants.filter { it.id in favoritePlantIds }
        } else {
            plants
        }

    init {
        loadPlants()
        loadFavorites()
    }

    fun onSearchTextChange(newValue: String) {
        searchText = newValue
        if (newValue.isNotEmpty()) {
            searchPlants(newValue)
        } else {
            loadPlants()
        }
    }

    fun toggleFavoritesFilter(enable: Boolean? = null) {
        isFavoritesFilterActive = enable ?: !isFavoritesFilterActive
    }

    fun loadFavorites() {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            favoriteRepository.getFavoritePlantIds(uid).fold(
                onSuccess = { ids ->
                    favoritePlantIds = ids.toSet()
                },
                onFailure = { e ->
                    Log.e("HomeViewModel", "Error al cargar los favoritos para el usuario $uid", e)
                }
            )
        }
    }

    var userMessage by mutableStateOf<String?>(null)
        private set

    fun userMessageShown() {
        userMessage = null
    }

    fun toggleFavorite(plantId: String) {
        val uid = authRepository.currentUser?.uid
        if (uid == null) {
            userMessage = "Debes iniciar sesión para guardar favoritos"
            return
        }

        val isCurrentlyFavorite = favoritePlantIds.contains(plantId)
        
        // Actualización optimista del estado local para reflejo inmediato en la UI
        favoritePlantIds = if (isCurrentlyFavorite) {
            favoritePlantIds - plantId
        } else {
            favoritePlantIds + plantId
        }

        viewModelScope.launch {
            val result = if (isCurrentlyFavorite) {
                favoriteRepository.removeFavorite(uid, plantId)
            } else {
                favoriteRepository.addFavorite(uid, plantId)
            }

            result.fold(
                onSuccess = {
                    userMessage = if (isCurrentlyFavorite) {
                        "Planta eliminada de tus favoritos"
                    } else {
                        "¡Planta guardada en tus favoritos!"
                    }
                },
                onFailure = { e ->
                    Log.e("HomeViewModel", "Error en Firestore al modificar favorito de la planta $plantId", e)
                    // Revertir cambio optimista
                    favoritePlantIds = if (isCurrentlyFavorite) {
                        favoritePlantIds + plantId
                    } else {
                        favoritePlantIds - plantId
                    }
                    userMessage = "No se pudo actualizar tus favoritos. Inténtalo de nuevo"
                }
            )
        }
    }

    fun retry() {
        if (searchText.isNotEmpty()) {
            searchPlants(searchText)
        } else {
            loadPlants()
        }
        loadFavorites()
    }

    fun loadPlants() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            repository.getAllPlants().fold(
                onSuccess = { 
                    plants = it
                    errorMessage = null
                },
                onFailure = { e ->
                    Log.e("HomeViewModel", "Error al cargar plantas desde la fuente de datos", e)
                    errorMessage = e.localizedMessage ?: "Error al cargar las plantas. Verifica tu conexión o permisos."
                }
            )
            isLoading = false
        }
    }

    private fun searchPlants(query: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            repository.searchPlants(query).fold(
                onSuccess = { 
                    plants = it
                    errorMessage = null
                },
                onFailure = { e ->
                    Log.e("HomeViewModel", "Error al buscar plantas con la consulta '$query'", e)
                    errorMessage = e.localizedMessage ?: "Error en la búsqueda de plantas."
                }
            )
            isLoading = false
        }
    }
}
