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
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.plantcarehomepgc.data.model.Plant
import com.example.plantcarehomepgc.presentation.components.FavoriteMessageBubble

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onProfileClick: () -> Unit,
    onPlantClick: (Plant) -> Unit = {}
) {
    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = !viewModel.isFavoritesFilterActive,
                    onClick = { viewModel.toggleFavoritesFilter(false) },
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
                    selected = viewModel.isFavoritesFilterActive,
                    onClick = { viewModel.toggleFavoritesFilter(true) },
                    icon = { 
                        Icon(
                            imageVector = if (viewModel.isFavoritesFilterActive) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder, 
                            contentDescription = "Favoritos"
                        ) 
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFE53935),
                        indicatorColor = Color(0xFFFFEBEE)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onProfileClick,
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") }
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
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
                    ActionChip(
                        icon = if (viewModel.isFavoritesFilterActive) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder, 
                        label = "Favoritos",
                        isSelected = viewModel.isFavoritesFilterActive,
                        onClick = { viewModel.toggleFavoritesFilter() }
                    )
                    ActionChip(icon = Icons.Default.History, label = "Historial")
                    ActionChip(icon = Icons.AutoMirrored.Filled.List, label = "Riegos")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = if (viewModel.isFavoritesFilterActive) 
                        "Mis Plantas Favoritas" 
                    else if (viewModel.searchText.isEmpty()) 
                        "Explorar Plantas" 
                    else 
                        "Resultados de búsqueda",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                val displayedPlants = viewModel.displayedPlants

                if (viewModel.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color.Black)
                    }
                } else if (viewModel.errorMessage != null) {
                    // Estado de Error
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = "Error",
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Error al cargar el catálogo",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = viewModel.errorMessage ?: "Ocurrió un error inesperado.",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.retry() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Reintentar")
                        }
                    }
                } else if (displayedPlants.isEmpty()) {
                    // Carga exitosa pero con cero resultados
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = if (viewModel.isFavoritesFilterActive)
                                "No tienes plantas guardadas en tus favoritos"
                            else if (viewModel.searchText.isEmpty()) 
                                "No hay plantas registradas en el catálogo" 
                            else 
                                "No se encontraron resultados para \"${viewModel.searchText}\"",
                            color = Color.Gray,
                            fontSize = 15.sp
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(displayedPlants) { plant ->
                            PlantItem(
                                plant = plant,
                                isFavorite = plant.id in viewModel.favoritePlantIds,
                                onFavoriteClick = { viewModel.toggleFavorite(plant.id) },
                                onClick = { onPlantClick(plant) }
                            )
                        }
                    }
                }
            }

            // Notificación flotante con animación sutil y forma de píldora
            FavoriteMessageBubble(
                message = viewModel.userMessage,
                onDismiss = { viewModel.userMessageShown() },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
fun PlantItem(
    plant: Plant,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {},
    onClick: () -> Unit
) {
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
            // Imagen de la planta con indicador de carga, diagnóstico y reintento
            Surface(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                color = Color(0xFFE0E0E0)
            ) {
                if (plant.imageUrl.isNotBlank()) {
                    var retryHash by remember(plant.id) { mutableIntStateOf(0) }
                    val context = LocalContext.current
                    val imageRequest = remember(plant.imageUrl, retryHash) {
                        ImageRequest.Builder(context)
                            .data(plant.imageUrl)
                            .crossfade(true)
                            .listener(
                                onError = { _, result ->
                                    val safeUrl = if (plant.imageUrl.length > 35) plant.imageUrl.take(35) + "..." else plant.imageUrl
                                    Log.e("HomeScreenImage", "Error cargando imagen para '${plant.nombre}' ($safeUrl): ${result.throwable.localizedMessage}", result.throwable)
                                }
                            )
                            .build()
                    }

                    SubcomposeAsyncImage(
                        model = imageRequest,
                        contentDescription = plant.nombre,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        loading = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.Black,
                                    strokeWidth = 2.dp
                                )
                            }
                        },
                        error = {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { retryHash++ }
                                    .padding(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Reintentar carga de imagen",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.Gray
                                )
                                Text(
                                    text = "Reintentar",
                                    fontSize = 9.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Yard,
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp),
                        tint = Color.Gray
                    )
                }
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

            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isFavorite) "Quitar de favoritos" else "Agregar a favoritos",
                    tint = if (isFavorite) Color(0xFFE53935) else Color.Black
                )
            }
        }
    }
}

@Composable
fun ActionChip(
    icon: ImageVector,
    label: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    OutlinedCard(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = if (isSelected) Color(0xFFFFEBEE) else Color.White
        ),
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
                modifier = Modifier.size(18.dp),
                tint = if (isSelected) Color(0xFFE53935) else Color.Black
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label, 
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Color(0xFFE53935) else Color.Black
            )
        }
    }
}
