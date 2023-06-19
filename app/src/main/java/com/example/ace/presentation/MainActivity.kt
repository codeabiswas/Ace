/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.ace.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.ace.presentation.theme.AceTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
////            AceTheme {
////                WearApp()
////            GameScoreScreen(totalGames = 7)
//            GameNumSelectionScreen()
////            }
        }
    }
}

@Composable
fun WearApp() {
    AceTheme {
        // Create a NavController
        val navController = rememberSwipeDismissableNavController()

        // Create a SwipeDismissableNavHost with a NavGraphbuilder lambda
        SwipeDismissableNavHost(navController = navController, startDestination = "GameNumSelectionScreen") {
            // Add a composable destination
            composable("GameNumSelectionScreen") {
                GameNumSelectionScreen(navController = navController)
            }

            // Add a composable destination
            composable("GameScoreScreen/{totalGames}",
                arguments = listOf(navArgument("totalGames") {
                    type = NavType.IntType
                })) {
                // Get the argument value from the back stack entry
                val totalGames = it.arguments?.getInt("totalGames") ?: 1
                GameScoreScreen(navController = navController, totalGames = totalGames)
            }

        }

    }
}
@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun WearAppPreview() {
    WearApp()
}