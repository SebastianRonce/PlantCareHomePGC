package com.example.plantcarehomepgc.data.repository

import com.example.plantcarehomepgc.data.model.Plant
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PlantRepository(private val firestore: FirebaseFirestore) {

    private val plantsCollection = firestore.collection("plants")

    suspend fun getAllPlants(): Result<List<Plant>> {
        return try {
            val snapshot = plantsCollection.get().await()
            val plants = snapshot.toObjects(Plant::class.java)
            Result.success(plants)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Busca plantas por nombre o por código único.
     * RF-01: Búsqueda de plantas por nombre o código único.
     */
    suspend fun searchPlants(query: String): Result<List<Plant>> {
        return try {
            // Realizamos dos búsquedas en paralelo (nombre y código) y combinamos resultados
            val nameQuery = plantsCollection
                .whereGreaterThanOrEqualTo("nombre", query)
                .whereLessThanOrEqualTo("nombre", query + "\uf8ff")
                .get()

            val codeQuery = plantsCollection
                .whereEqualTo("codigo", query)
                .get()

            val nameSnapshot = nameQuery.await()
            val codeSnapshot = codeQuery.await()

            val nameResults = nameSnapshot.toObjects(Plant::class.java)
            val codeResults = codeSnapshot.toObjects(Plant::class.java)

            // Combinar y eliminar duplicados basados en ID
            val combinedResults = (nameResults + codeResults).distinctBy { it.id }
            
            Result.success(combinedResults)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPlantById(id: String): Result<Plant?> {
        return try {
            val document = plantsCollection.document(id).get().await()
            Result.success(document.toObject(Plant::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Funciones administrativas (Se usarán en la Fase 9)
    suspend fun savePlant(plant: Plant): Result<Unit> {
        return try {
            val docRef = if (plant.id.isEmpty()) plantsCollection.document() else plantsCollection.document(plant.id)
            val finalPlant = plant.copy(id = docRef.id)
            docRef.set(finalPlant).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
