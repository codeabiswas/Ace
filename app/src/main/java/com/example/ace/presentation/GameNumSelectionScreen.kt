package com.example.ace.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText


@Composable
fun GameNumSelectionScreen (navController: NavController) {

    var totalGames = remember {
        mutableStateOf(1)
    }

    // Use Scaffold to get a curved text time at the top, a vignette, and a scrolling indicator
    Scaffold(
        timeText = { TimeText() },
    ) {
        // Use ScalingLazyColumn to create a scrollable list of items that scale based on their position
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "How many games?"
            )

            Spacer(modifier = Modifier.height(8.dp))

            GameNumSelectionRow(totalGames)

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    Log.i("totalGames: ", "${totalGames.value}")
                    navController.navigate("GameScoreScreen/${totalGames.value}")
                }
            ) {
                Text(text = "Proceed")
            }

        }
    }
}


// A composable that represents a row of score components
@Composable
fun GameNumSelectionRow(gameNum: MutableState<Int>) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // A button to decrease the score
        Button(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = {
              gameNum.value--
            },
            enabled = gameNum.value > 1
        ){
            Text("-")
        }
        // A text box to show the score
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = "${gameNum.value}",
            style = MaterialTheme.typography.body1,
            fontSize = 35.sp
        )
        // A button to increase the score
        Button(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = {
              gameNum.value++
            },
            // Odd number because to consider tiebreak
            enabled = gameNum.value < 11
        ){
            Text("+")
        }

    }
}


//@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
//@Composable
//fun GameNumSelectionScreenPreview() {
//    GameNumSelectionScreen()
//}
