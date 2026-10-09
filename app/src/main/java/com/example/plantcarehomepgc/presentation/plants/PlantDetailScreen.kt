package com.example.plantcarehomepgc.presentation.plants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantDetailScreen(
    viewModel: PlantDetailViewModel,
    plantId: String,
    onBackClick: () -> Unit
) {
    LaunchedEffect(plantId) {
        viewModel.loadPlant(plantId)
    }

    val plant = viewModel.plant

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Planta", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Lógica de favoritos */ }) {
                        Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Favorito")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { padding ->
        if (viewModel.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Black)
            }
        } else if (plant != null) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Imagen de la planta (Placeholder)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFFF2F2F2)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Yard,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = plant.nombre,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = plant.nombreCientifico,
                    fontSize = 18.sp,
                    color = Color.Gray,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Surface(
                    color = Color(0xFFE8F5E9),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Código: ${plant.codigo}",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 14.sp,
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("Descripción", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = plant.descripcion,
                    fontSize = 15.sp,
                    color = Color.DarkGray,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text("Información de Cuidado", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    InfoCard(Modifier.weight(1f), Icons.Default.Thermostat, "Temp. Ideal", plant.temperatura)
                    Spacer(modifier = Modifier.width(12.dp))
                    InfoCard(Modifier.weight(1f), Icons.Default.WaterDrop, "Riego", plant.riego)
                }
                
                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    InfoCard(Modifier.weight(1f), Icons.Default.LightMode, "Iluminación", plant.iluminacion)
                    Spacer(modifier = Modifier.width(12.dp))
                    InfoCard(Modifier.weight(1f), Icons.Default.Opacity, "Humedad", plant.humedad)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("Suelo y Sustrato", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(plant.suelo, fontSize = 15.sp, color = Color.DarkGray)

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.TipsAndUpdates, contentDescription = null, tint = Color(0xFF558B2F))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Recomendaciones", fontWeight = FontWeight.Bold, color = Color(0xFF33691E))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(plant.recomendaciones, fontSize = 14.sp, color = Color(0xFF33691E))
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        } else if (viewModel.errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = viewModel.errorMessage!!, color = Color.Red)
            }
        }
    }
}

@Composable
fun InfoCard(modifier: Modifier, icon: ImageVector, label: String, value: String) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(label, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}
