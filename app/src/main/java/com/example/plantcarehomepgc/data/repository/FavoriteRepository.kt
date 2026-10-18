package com.example.plantcarehomepgc.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FavoriteRepository(private val firestore: FirebaseFirestore) {

    private fun getFavoritesCollection(uid: String) = 
        firestore.collection("users").document(uid).collection("favorites")

    suspend fun addFavorite(uid: String, plantId: String): Result<Unit> {
        if (uid.isBlank() || plantId.isBlank()) {
            Log.e("FavoriteRepository", "addFavorite falló: UID o plantId vacíos (uid='$uid', plantId='$plantId')")
            return Result.failure(IllegalArgumentException("UID de usuario o ID de planta no válidos."))
        }
        return try {
            getFavoritesCollection(uid).document(plantId).set(mapOf("plantId" to plantId)).await()
            Log.d("FavoriteRepository", "Favorito añadido exitosamente: users/$uid/favorites/$plantId")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FavoriteRepository", "Error en Firestore al guardar favorito (plantId='$plantId')", e)
            Result.failure(e)
        }
    }

    suspend fun removeFavorite(uid: String, plantId: String): Result<Unit> {
        if (uid.isBlank() || plantId.isBlank()) {
            Log.e("FavoriteRepository", "removeFavorite falló: UID o plantId vacíos (uid='$uid', plantId='$plantId')")
            return Result.failure(IllegalArgumentException("UID de usuario o ID de planta no válidos."))
        }
        return try {
            getFavoritesCollection(uid).document(plantId).delete().await()
            Log.d("FavoriteRepository", "Favorito eliminado exitosamente: users/$uid/favorites/$plantId")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FavoriteRepository", "Error en Firestore al eliminar favorito (plantId='$plantId')", e)
            Result.failure(e)
        }
    }

    suspend fun isFavorite(uid: String, plantId: String): Boolean {
        if (uid.isBlank() || plantId.isBlank()) return false
        return try {
            val document = getFavoritesCollection(uid).document(plantId).get().await()
            document.exists()
        } catch (e: Exception) {
            Log.e("FavoriteRepository", "Error al consultar si plantId '$plantId' es favorito de uid '$uid'", e)
            false
        }
    }

    suspend fun getFavoritePlantIds(uid: String): Result<List<String>> {
        if (uid.isBlank()) return Result.success(emptyList())
        return try {
            val snapshot = getFavoritesCollection(uid).get().await()
            val ids = snapshot.documents.map { it.id }.filter { it.isNotBlank() }
            Result.success(ids)
        } catch (e: Exception) {
            Log.e("FavoriteRepository", "Error al obtener lista de favoritos para uid '$uid'", e)
            Result.failure(e)
        }
    }
}
