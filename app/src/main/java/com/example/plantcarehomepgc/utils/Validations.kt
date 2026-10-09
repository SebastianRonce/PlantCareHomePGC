package com.example.plantcarehomepgc.utils

import android.util.Patterns

/**
 * Valida que el correo electrónico sea un formato válido.
 * Se aceptan todos los dominios de correo electrónico válidos.
 */
fun validateEmail(email: String): Pair<Boolean, String> {
    val trimmedEmail = email.trim()
    return when {
        trimmedEmail.isEmpty() -> Pair(false, "Ingrese su correo electrónico.")
        !Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches() -> Pair(false, "El formato del correo es inválido.")
        else -> Pair(true, "")
    }
}

/**
 * Valida la contraseña según requerimientos:
 * - Mínimo 8 caracteres.
 * - Al menos un número.
 * - Al menos un símbolo especial.
 */
fun validatePassword(password: String): Pair<Boolean, String> {
    val specialChars = "!@#$%^&*()_+-=[]{}|;':\",./<>?"
    return when {
        password.isEmpty() -> Pair(false, "Ingrese su contraseña.")
        password.length < 8 -> Pair(false, "La contraseña debe tener al menos 8 caracteres.")
        !password.any { it.isDigit() } -> Pair(false, "La contraseña debe contener al menos un número.")
        !password.any { it in specialChars } -> Pair(false, "La contraseña debe contener al menos un símbolo especial.")
        else -> Pair(true, "")
    }
}

/**
 * Valida el nombre del usuario.
 */
fun validateName(name: String): Pair<Boolean, String> {
    val trimmedName = name.trim()
    return when {
        trimmedName.isEmpty() -> Pair(false, "Ingrese su nombre.")
        trimmedName.length < 3 -> Pair(false, "El nombre debe tener al menos 3 caracteres.")
        else -> Pair(true, "")
    }
}

/**
 * Valida que la confirmación coincida con la contraseña original.
 */
fun validateConfirmPassword(password: String, confirmPassword: String): Pair<Boolean, String> {
    return when {
        confirmPassword.isEmpty() -> Pair(false, "Confirme su contraseña.")
        confirmPassword != password -> Pair(false, "Las contraseñas no coinciden.")
        else -> Pair(true, "")
    }
}
