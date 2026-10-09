package com.example.plantcarehomepgc.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcarehomepgc.data.model.Plant
import com.example.plantcarehomepgc.data.repository.PlantRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PlantRepository) : ViewModel() {
    
    var searchText by mutableStateOf("")
        private set

    var plants by mutableStateOf<List<Plant>>(emptyList())
        private set
        
    var isLoading by mutableStateOf(false)
        private set
        
    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadPlants()
    }

    fun onSearchTextChange(newValue: String) {
        searchText = newValue
        if (newValue.isNotEmpty()) {
            searchPlants(newValue)
        } else {
            loadPlants()
        }
    }

    private fun loadPlants() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            repository.getAllPlants().fold(
                onSuccess = { plants = it },
                onFailure = { errorMessage = "Error al cargar las plantas" }
            )
            isLoading = false
        }
    }

    private fun searchPlants(query: String) {
        viewModelScope.launch {
            isLoading = true
            repository.searchPlants(query).fold(
                onSuccess = { plants = it },
                onFailure = { errorMessage = "Error en la búsqueda" }
            )
            isLoading = false
        }
    }
}
