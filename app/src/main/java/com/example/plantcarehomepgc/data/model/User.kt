package com.example.plantcarehomepgc.data.model

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "user",
    val createdAt: Long = System.currentTimeMillis()
)
