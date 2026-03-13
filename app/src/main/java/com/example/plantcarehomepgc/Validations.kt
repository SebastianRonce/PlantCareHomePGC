package com.example.plantcarehomepgc

import android.util.Patterns

// Return un true si es valido un false si no es valido
//  tambien una cadena de texto que diga que paso
fun validateEmail(email: String): Pair<Boolean, String>{
    return  when{
        email.isEmpty() -> Pair(false, "Ingrese su correo. ")
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> Pair(false, "El correo es invalido. ")
        !email.endsWith("@gmail.com") -> Pair(false, "El correo no es valido. ")
        else -> Pair(true, "")
    }
}

fun validatePassword(password: String): Pair <Boolean, String>{
    return when{
        password.isEmpty()-> Pair(false, "Ingrese su contraseña. ")
        password.length < 8 -> Pair(false, "La contraseña debe tener al menos 8 caracteres. ")
        !password.any{ it.isDigit() } -> Pair(false, "La contraseña debe contener al menos un numero. ")
        else -> Pair(true, "")

    }
}

fun validateName(name: String): Pair <Boolean, String>{
    return when {
        name.isEmpty() -> Pair(false, "Ingrese su nombre. ")
        name.length < 5 -> Pair(false, "El nombre debe tener al menos 5 caracteres. ")
        else -> Pair(true, "")
    }
}

fun validateConfirmPassword(password: String, confirmPassword: String): Pair <Boolean, String>{
    return when {
        confirmPassword.isEmpty() -> Pair(false, "Ingrese una contraseña. ")
        confirmPassword != password -> Pair(false, "Las contraseñas no coinciden. ")
        else -> Pair(true, "")

    }
}