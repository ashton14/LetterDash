package com.zybooks.letterdash

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
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
    gameViewModel: GameViewModel = viewModel(),
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
                    gameViewModel.resetGame()
                },
                gameViewModel = gameViewModel
            )
        }
        composable<Routes.Game> {
            GameScreen(
                onTimerEnd = {
                    navController.navigate(Routes.GameOver)
                    gameViewModel.updateHighScore()
                },
                gameViewModel = gameViewModel
            )
        }
        composable<Routes.GameOver> {
            GameOverScreen(
                onPlayAgainClick = {
                    navController.navigate(Routes.Game)
                    gameViewModel.resetGame()
                },
                onHomeClick = {
                    navController.navigate(Routes.Home)
                },
                gameViewModel = gameViewModel
            )
        }
    }
}