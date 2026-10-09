package com.example.plantcarehomepgc.presentation.plants

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcarehomepgc.data.model.Plant
import com.example.plantcarehomepgc.data.repository.PlantRepository
import kotlinx.coroutines.launch

class PlantDetailViewModel(private val repository: PlantRepository) : ViewModel() {

    var plant by mutableStateOf<Plant?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadPlant(plantId: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            repository.getPlantById(plantId).fold(
                onSuccess = { plant = it },
                onFailure = { errorMessage = "Error al cargar los detalles de la planta" }
            )
            isLoading = false
        }
    }
}
