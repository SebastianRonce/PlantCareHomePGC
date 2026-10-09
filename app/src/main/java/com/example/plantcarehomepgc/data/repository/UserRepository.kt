package com.example.plantcarehomepgc.data.repository

import com.example.plantcarehomepgc.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository(private val firestore: FirebaseFirestore) {

    private val usersCollection = firestore.collection("users")

    suspend fun createUser(user: User): Result<Unit> {
        return try {
            usersCollection.document(user.uid).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUser(uid: String): Result<User?> {
        return try {
            val document = usersCollection.document(uid).get().await()
            if (document.exists()) {
                Result.success(document.toObject(User::class.java))
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
