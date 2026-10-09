package com.example.plantcarehomepgc.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    viewModel: ProfileViewModel,
    onLogout: () -> Unit,
    onHomeClick: () -> Unit
) {
    val context = LocalContext.current
    
    // Estados locales para el diálogo de edición
    var showEditDialog by remember { mutableStateOf(false) }
    var newDisplayName by remember { mutableStateOf(viewModel.currentName) }

    // Diálogo para editar el nombre
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Editar Nombre") },
            text = {
                OutlinedTextField(
                    value = newDisplayName,
                    onValueChange = { newDisplayName = it },
                    label = { Text("Nuevo nombre") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.updateName(newDisplayName) { success ->
                        if (success) {
                            showEditDialog = false
                            Toast.makeText(context, "Nombre actualizado", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show()
                        }
                    }
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Perfil", fontWeight = FontWeight.SemiBold) },
                actions = {
                    IconButton(onClick = { /* Acción de ajustes */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Ajustes")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = onHomeClick,
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Calendario") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Favoritos") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        indicatorColor = Color(0xFFF0F0F0)
                    )
                )
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Avatar circular negro
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(70.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = viewModel.currentName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = viewModel.email,
                fontSize = 15.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Lista de opciones
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFFAFAFA),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF0F0F0))
            ) {
                Column {
                    ProfileOptionRow(
                        icon = Icons.Default.Person,
                        title = "Nombre de usuario",
                        subtitle = viewModel.currentName,
                        onClick = { 
                            newDisplayName = viewModel.currentName
                            showEditDialog = true 
                        }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color(0xFFEEEEEE))
                    ProfileOptionRow(
                        icon = Icons.Default.Email,
                        title = "Correo electrónico",
                        subtitle = viewModel.email
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color(0xFFEEEEEE))
                    ProfileOptionRow(
                        icon = Icons.Default.DateRange,
                        title = "Fecha de registro",
                        subtitle = viewModel.registrationDate
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color(0xFFEEEEEE))
                    ProfileOptionRow(
                        icon = Icons.Default.Lock,
                        title = "Seguridad",
                        subtitle = "Cambiar contraseña"
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color(0xFFEEEEEE))
                    ProfileOptionRow(
                        icon = Icons.Default.Notifications,
                        title = "Notificaciones",
                        subtitle = "Gestiona tus preferencias"
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón cerrar sesión
            Button(
                onClick = {
                    viewModel.logout()
                    onLogout()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Cerrar sesión", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ProfileOptionRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(22.dp),
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
            Text(text = subtitle, fontSize = 13.sp, color = Color.Gray)
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color.LightGray
        )
    }
}
