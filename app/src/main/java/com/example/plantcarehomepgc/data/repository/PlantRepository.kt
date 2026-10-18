package com.example.plantcarehomepgc.data.repository

import android.util.Log
import com.example.plantcarehomepgc.data.model.Plant
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PlantRepository(private val firestore: FirebaseFirestore) {

    private val plantsCollection = firestore.collection("plants")

    private fun DocumentSnapshot.toPlant(): Plant? {
        if (!exists()) return null
        return try {
            val plant = toObject(Plant::class.java)
            if (plant != null) {
                val finalId = if (plant.id.isNotBlank()) plant.id else id
                plant.copy(id = finalId)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.w("PlantRepository", "Fallback al mapeo manual para documento $id por diferencia de tipos", e)
            try {
                val humedadRaw = get("humedad")
                val humedadInt = when (humedadRaw) {
                    is Number -> humedadRaw.toInt()
                    is String -> humedadRaw.replace("%", "").trim().toIntOrNull() ?: 0
                    else -> 0
                }

                val createdAtRaw = get("createdAt")
                val createdAtLong = when (createdAtRaw) {
                    is Number -> createdAtRaw.toLong()
                    is Timestamp -> createdAtRaw.toDate().time
                    is String -> createdAtRaw.toLongOrNull() ?: System.currentTimeMillis()
                    else -> System.currentTimeMillis()
                }

                val updatedAtRaw = get("updatedAt")
                val updatedAtLong = when (updatedAtRaw) {
                    is Number -> updatedAtRaw.toLong()
                    is Timestamp -> updatedAtRaw.toDate().time
                    is String -> updatedAtRaw.toLongOrNull() ?: System.currentTimeMillis()
                    else -> System.currentTimeMillis()
                }

                Plant(
                    id = if (getString("id").isNullOrEmpty()) id else getString("id")!!,
                    codigo = getString("codigo") ?: "",
                    nombre = getString("nombre") ?: "",
                    nombreCientifico = getString("nombreCientifico") ?: "",
                    descripcion = getString("descripcion") ?: "",
                    temperatura = getString("temperatura") ?: "",
                    riego = getString("riego") ?: "",
                    frecuenciaRiego = getString("frecuenciaRiego") ?: "",
                    iluminacion = getString("iluminacion") ?: "",
                    humedad = humedadInt,
                    suelo = getString("suelo") ?: "",
                    recomendaciones = getString("recomendaciones") ?: "",
                    imageUrl = getString("imageUrl") ?: "",
                    createdAt = createdAtLong,
                    updatedAt = updatedAtLong
                )
            } catch (manualEx: Exception) {
                Log.e("PlantRepository", "Error crítico al mapear el documento $id a Plant", manualEx)
                null
            }
        }
    }

    suspend fun getAllPlants(): Result<List<Plant>> {
        return try {
            val snapshot = plantsCollection.get().await()
            val plants = snapshot.documents.mapNotNull { it.toPlant() }
            Result.success(plants)
        } catch (e: Exception) {
            Log.e("PlantRepository", "Error al obtener las plantas desde Firestore", e)
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

            val nameResults = nameSnapshot.documents.mapNotNull { it.toPlant() }
            val codeResults = codeSnapshot.documents.mapNotNull { it.toPlant() }

            // Combinar y eliminar duplicados basados en ID
            val combinedResults = (nameResults + codeResults).distinctBy { it.id }
            
            Result.success(combinedResults)
        } catch (e: Exception) {
            Log.e("PlantRepository", "Error al buscar plantas con la consulta: $query", e)
            Result.failure(e)
        }
    }

    suspend fun getPlantById(id: String): Result<Plant?> {
        return try {
            val document = plantsCollection.document(id).get().await()
            Result.success(document.toPlant())
        } catch (e: Exception) {
            Log.e("PlantRepository", "Error al obtener la planta por ID: $id", e)
            Result.failure(e)
        }
    }

    // Funciones administrativas (Se usarán en la Fase 9)
    suspend fun savePlant(plant: Plant): Result<Unit> {
        return try {
            val docRef = if (plant.id.isEmpty()) plantsCollection.document() else plantsCollection.document(plant.id)
            val currentTime = System.currentTimeMillis()
            val finalPlant = plant.copy(
                id = docRef.id,
                createdAt = if (plant.createdAt == 0L) currentTime else plant.createdAt,
                updatedAt = currentTime
            )
            docRef.set(finalPlant).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("PlantRepository", "Error al guardar la planta", e)
            Result.failure(e)
        }
    }
}
