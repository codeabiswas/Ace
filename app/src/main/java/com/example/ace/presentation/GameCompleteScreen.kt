package com.example.ace.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController

@Composable
fun GameCompleteScreen (navController: NavController, winState: Int) {

    val contentString: String

    if (winState == 0) {
        contentString = "P1 and P2 draw"
    } else if (winState == 1) {
        contentString = "P1 won"
    } else if (winState == 2) {
        contentString = "P2 won"
    } else {
        contentString = "Unknown state"
    }

    Scaffold (
        timeText = { TimeText() }
    ) {
      Column(
          modifier = Modifier
              .fillMaxSize(),
          verticalArrangement = Arrangement.Center
      ) {

          Text(
              modifier = Modifier.fillMaxWidth(),
              textAlign = TextAlign.Center,
              text = contentString
          )

          Spacer(modifier = Modifier.height(8.dp))

          Button(
              modifier = Modifier.fillMaxWidth(),
              onClick = {
                  navController.navigate("GameNumSelectionScreen")
              }
          ) {
              Text(text="New game")
          }

      }
    }

}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun GameCompleteScreenPreview() {
    GameCompleteScreen(navController = rememberSwipeDismissableNavController(), winState = 0)
}
