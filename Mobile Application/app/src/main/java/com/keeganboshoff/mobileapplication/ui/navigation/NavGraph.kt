package com.keeganboshoff.mobileapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.keeganboshoff.mobileapplication.ui.screens.HomeScreen
import com.keeganboshoff.mobileapplication.ui.screens.LoginScreen
import com.keeganboshoff.mobileapplication.ui.screens.RegisterScreen
import com.keeganboshoff.mobileapplication.ui.screens.RegistrationSuccessScreen

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
                onRegisterSuccess = {
                    navController.navigate("reg_success") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        } // End Register

        // Registration Success
        composable("reg_success") {
            RegistrationSuccessScreen(
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("reg_success") { inclusive = true }
                    }
                }
            )
        } // End Registration Success

        // Home
        composable("home") {
            HomeScreen()
        }
    }
}

