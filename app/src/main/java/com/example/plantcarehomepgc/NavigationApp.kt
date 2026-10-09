package com.example.plantcarehomepgc

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.example.plantcarehomepgc.data.repository.AuthRepository
import com.example.plantcarehomepgc.data.repository.PlantRepository
import com.example.plantcarehomepgc.data.repository.UserRepository
import com.example.plantcarehomepgc.presentation.auth.*
import com.example.plantcarehomepgc.presentation.home.HomeScreen
import com.example.plantcarehomepgc.presentation.home.HomeViewModel
import com.example.plantcarehomepgc.presentation.plants.PlantDetailScreen
import com.example.plantcarehomepgc.presentation.plants.PlantDetailViewModel
import com.example.plantcarehomepgc.presentation.profile.PerfilScreen
import com.example.plantcarehomepgc.presentation.profile.ProfileViewModel
import com.example.plantcarehomepgc.presentation.splash.SplashScreen
import com.example.plantcarehomepgc.presentation.splash.SplashViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    
    // Inicialización de Firebase y Repositorios
    val auth = Firebase.auth
    val firestore = Firebase.firestore
    
    val authRepository = AuthRepository(auth)
    val userRepository = UserRepository(firestore)
    val plantRepository = PlantRepository(firestore)
    
    val authFactory = AuthViewModelFactory(authRepository, userRepository, plantRepository)

    NavHost(
        navController = navController,
        startDestination = "splash",
    ) {
        composable("splash") {
            val splashViewModel: SplashViewModel = viewModel(factory = authFactory)
            SplashScreen(
                viewModel = splashViewModel,
                onNavigate = { destination ->
                    navController.navigate(destination) {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }
        composable("login") {
            val loginViewModel: LoginViewModel = viewModel(factory = authFactory)
            LoginScreen(
                viewModel = loginViewModel,
                onClickRegister = { navController.navigate("register") },
                onSuccessfulLogin = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("register") {
            val registerViewModel: RegisterViewModel = viewModel(factory = authFactory)
            RegisterScreen(
                viewModel = registerViewModel,
                onClickBack = { navController.popBackStack() },
                onSuccessfulRegister = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            val homeViewModel: HomeViewModel = viewModel(factory = authFactory)
            HomeScreen(
                viewModel = homeViewModel,
                onProfileClick = { navController.navigate("perfil") },
                onPlantClick = { plant ->
                    navController.navigate("plant_detail/${plant.id}")
                }
            )
        }
        composable(
            route = "plant_detail/{plantId}",
            arguments = listOf(navArgument("plantId") { type = NavType.StringType })
        ) { backStackEntry ->
            val plantId = backStackEntry.arguments?.getString("plantId") ?: ""
            val detailViewModel: PlantDetailViewModel = viewModel(factory = authFactory)
            PlantDetailScreen(
                viewModel = detailViewModel,
                plantId = plantId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("perfil") {
            val profileViewModel: ProfileViewModel = viewModel(factory = authFactory)
            PerfilScreen(
                viewModel = profileViewModel,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onHomeClick = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    }
}
