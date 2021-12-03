package com.sebastianamhj.schoolfinalproject.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sebastianamhj.schoolfinalproject.ui.main.MainView
import com.sebastianamhj.schoolfinalproject.ui.main.UserDeatilView

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "MainView") {
        composable("MainView") {
            MainView(navController = navController)
        }
        composable("UserDetailView/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            UserDeatilView(
                navController = navController, backStackEntry.arguments?.getString("userId")
            )
        }
    }
}