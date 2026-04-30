package com.keeganboshoff.mobileapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.keeganboshoff.mobileapplication.ui.screens.HomeScreen
import com.keeganboshoff.mobileapplication.ui.screens.LoginScreen
import com.keeganboshoff.mobileapplication.ui.screens.RegisterScreen
import com.keeganboshoff.mobileapplication.ui.screens.RegistrationSuccessScreen
import com.keeganboshoff.mobileapplication.ui.screens.StartSessionScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // Login
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        } // End Login

        // Register
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { fullName ->
                    navController.navigate("reg_success/${fullName}") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        } // End Register

        // Registration Success
        composable("reg_success/{fullName}") { backStackEntry ->
            val fullName = backStackEntry.arguments?.getString("fullName") ?: "User"
            RegistrationSuccessScreen(
                fullName = fullName,
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("reg_success") { inclusive = true }
                    }
                }
            )
        } // End Registration Success

        // Home
        composable("home") {
            HomeScreen(
                onNavigateToStartSession = {
                    navController.navigate("start_session") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        } // End Home

        // Start Session
        composable("start_session") {
            StartSessionScreen(
                onStartSessionSuccess = {
                    navController.navigate("home") {
                        popUpTo("start_session") { inclusive = true }
                    }
                }
            )
        }
    }
}

