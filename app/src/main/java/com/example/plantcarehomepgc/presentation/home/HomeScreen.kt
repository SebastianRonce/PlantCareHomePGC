package com.example.plantcarehomepgc.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantcarehomepgc.data.model.Plant

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onProfileClick: () -> Unit,
    onPlantClick: (Plant) -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        indicatorColor = Color(0xFFE0E0E0)
                    )
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
                    selected = false,
                    onClick = onProfileClick,
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Barra de búsqueda
            TextField(
                value = viewModel.searchText,
                onValueChange = { viewModel.onSearchTextChange(it) },
                placeholder = { Text("Buscar por nombre o código", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF2F2F2),
                    unfocusedContainerColor = Color(0xFFF2F2F2),
                    disabledContainerColor = Color(0xFFF2F2F2),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Fila de botones de acción rápida
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ActionChip(icon = Icons.Outlined.FavoriteBorder, label = "Favoritos")
                ActionChip(icon = Icons.Default.History, label = "Historial")
                ActionChip(icon = Icons.AutoMirrored.Filled.List, label = "Riegos")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = if (viewModel.searchText.isEmpty()) "Explorar Plantas" else "Resultados de búsqueda",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (viewModel.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.Black)
                }
            } else if (viewModel.plants.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = if (viewModel.searchText.isEmpty()) "No hay plantas registradas" else "No se encontraron resultados",
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(viewModel.plants) { plant ->
                        PlantItem(plant = plant, onClick = { onPlantClick(plant) })
                    }
                }
            }
        }
    }
}

@Composable
fun PlantItem(plant: Plant, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder para la imagen de la planta
            Surface(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                color = Color(0xFFE0E0E0)
            ) {
                Icon(
                    imageVector = Icons.Default.Yard,
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp),
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = plant.nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = plant.nombreCientifico,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Código: ${plant.codigo}",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }

            IconButton(onClick = { /* Lógica de favoritos */ }) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Agregar a favoritos",
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
fun ActionChip(icon: ImageVector, label: String) {
    OutlinedCard(
        onClick = { },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
        modifier = Modifier.wrapContentWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label, 
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
