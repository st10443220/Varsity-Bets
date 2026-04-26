package com.keeganboshoff.mobileapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.keeganboshoff.mobileapplication.ui.screens.LoginScreen
import com.keeganboshoff.mobileapplication.ui.screens.RegisterScreen

@Composable
fun NavGraph(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // Login
        composable("login"){
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") {inclusive = true}
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
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        } // End Register
    }
}

