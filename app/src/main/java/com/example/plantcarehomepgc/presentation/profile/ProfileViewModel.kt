package com.example.plantcarehomepgc.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.plantcarehomepgc.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import java.text.SimpleDateFormat
import java.util.*

class ProfileViewModel(private val repository: AuthRepository) : ViewModel() {

    val user: FirebaseUser? = repository.currentUser
    
    var currentName by mutableStateOf(user?.displayName ?: "Usuario")
        private set
        
    val email: String = user?.email ?: "No disponible"
    
    val registrationDate: String = user?.metadata?.creationTimestamp?.let {
        if (it != 0L) {
            val sdf = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale.forLanguageTag("es-ES"))
            sdf.format(Date(it))
        } else "No disponible"
    } ?: "No disponible"

    fun updateName(newName: String, onResult: (Boolean) -> Unit) {
        if (newName.isNotBlank()) {
            val profileUpdates = userProfileChangeRequest {
                displayName = newName
            }
            user?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    currentName = newName
                    onResult(true)
                } else {
                    onResult(false)
                }
            }
        }
    }

    fun logout() {
        repository.signOut()
    }
}
