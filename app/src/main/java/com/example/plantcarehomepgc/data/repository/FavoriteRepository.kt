package com.example.plantcarehomepgc.data.repository

import com.example.plantcarehomepgc.data.model.Plant
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FavoriteRepository(private val firestore: FirebaseFirestore) {

    private fun getFavoritesCollection(uid: String) = 
        firestore.collection("users").document(uid).collection("favorites")

    suspend fun addFavorite(uid: String, plantId: String): Result<Unit> {
        return try {
            getFavoritesCollection(uid).document(plantId).set(mapOf("plantId" to plantId)).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun removeFavorite(uid: String, plantId: String): Result<Unit> {
        return try {
            getFavoritesCollection(uid).document(plantId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun isFavorite(uid: String, plantId: String): Boolean {
        return try {
            val document = getFavoritesCollection(uid).document(plantId).get().await()
            document.exists()
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getFavoritePlantIds(uid: String): Result<List<String>> {
        return try {
            val snapshot = getFavoritesCollection(uid).get().await()
            val ids = snapshot.documents.map { it.id }
            Result.success(ids)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
