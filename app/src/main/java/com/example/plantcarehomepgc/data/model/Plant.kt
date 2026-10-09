package com.example.plantcarehomepgc.data.model

data class Plant(
    val id: String = "",
    val codigo: String = "",
    val nombre: String = "",
    val nombreCientifico: String = "",
    val descripcion: String = "",
    val temperatura: String = "",
    val riego: String = "",
    val iluminacion: String = "",
    val humedad: String = "",
    val suelo: String = "",
    val recomendaciones: String = "",
    val imageUrl: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
