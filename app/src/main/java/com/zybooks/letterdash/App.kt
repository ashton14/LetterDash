package com.zybooks.letterdash

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object Home

    @Serializable
    data object Game

    @Serializable
    data object GameOver
}

@Composable
fun App(
    gameViewModel: GameViewModel = GameViewModel(),
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Home
    ) {
        composable<Routes.Home> {
            HomeScreen(
                highScore = gameViewModel.getHighScore(),
                onPlayClick = {
                    navController.navigate(Routes.Game)
                }
            )
        }
        composable<Routes.Game> {
            GameScreen(
                score = 0 ,
                onTimerEnd = {
                    navController.navigate(Routes.GameOver)
                },
            )
        }
        composable<Routes.GameOver> {
            GameOverScreen(
                onPlayAgainClick = {
                    navController.navigate(Routes.Game)
                },
                onHomeClick = {
                    navController.navigate(Routes.Home)
                }
            )
        }
    }
}