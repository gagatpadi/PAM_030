package com.example.mykonter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.mykonter.view.*

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.HOME
    ) {
        composable(Route.HOME) {
            HalamanHome(navController)
        }
        composable(Route.SELESAI) {
            HalamanSelesai(navController)
        }
        composable(Route.RIWAYAT) {
            HalamanRiwayat(navController)
        }
    }
}
