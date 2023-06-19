package com.example.ace.presentation

import android.util.Log
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.foundation.CurvedTextStyle
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.TimeTextDefaults
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.curvedText
import androidx.wear.compose.material.rememberScalingLazyListState
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GameScoreScreen (navController: NavController, totalGames: Int) {
    val leadingTextStyle = TimeTextDefaults.timeTextStyle(color = MaterialTheme.colors.primary)

    val listState = rememberScalingLazyListState()

    var currGame by remember {
        mutableStateOf(1)
    }

    var p1Score = remember {
        mutableStateOf(0)
    }

    var p2Score = remember {
        mutableStateOf(0)
    }

    var p1GameScoreString = remember {
        mutableStateOf("00")
    }
    var p2GameScoreString = remember {
        mutableStateOf("00")
    }

    var p1GameWinString by remember {
        mutableStateOf("P1 won game(s) ")
    }

    var p2GameWinString by remember {
        mutableStateOf("P2 won game(s) ")
    }

    // Use Scaffold to get a curved text time at the top, a vignette, and a scrolling indicator
    Scaffold(
        timeText = { TimeText(
            startLinearContent = {
                Text(
                    text = "Game $currGame of $totalGames",
                    style = leadingTextStyle
                )
            },
            startCurvedContent = {
                curvedText(
                    text = "Game $currGame of $totalGames",
                    style = CurvedTextStyle(leadingTextStyle)
                )
            },
        ) },

        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        positionIndicator = {
            PositionIndicator(
                scalingLazyListState = listState
            )
        }
    ) {

        val focusRequester = remember { FocusRequester() }
        val coroutingScope = rememberCoroutineScope()

        // Use ScalingLazyColumn to create a scrollable list of items that scale based on their position
//        Column(
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .onRotaryScrollEvent {
                                     coroutingScope.launch {
                                         listState.scrollBy(it.verticalScrollPixels)
                                     }
                    true
                }
                .focusRequester(focusRequester)
                .focusable()
            ,
//            verticalArrangement = Arrangement.Center
            state = listState
        ) {

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "P1"
                )
            }

            item {
                ScoreRow(score = p1Score, scoreString = p1GameScoreString)
            }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "P2"
                )
            }

            item {
                ScoreRow(score = p2Score, scoreString = p2GameScoreString)
            }

            item {
                Text(p1GameWinString)
            }

            item {
                Text(p2GameWinString)
            }

            item {
                // Button to proceed to next game
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        Log.i("Curr game scores: ", "P1: ${p1Score.value} | P2: ${p2Score.value}")
                        if (currGame <= totalGames) {

                            if (p1Score.value > p2Score.value) {
                                p1GameWinString += if (p1GameWinString == "P1 won game(s) ") {
                                    "$currGame"
                                } else {
                                    ", $currGame"
                                }
                            } else {
                                p2GameWinString += if (p2GameWinString == "P2 won game(s) ") {
                                    "$currGame"
                                } else {
                                    ", $currGame"
                                }
                            }

                            currGame++

                            // Reset the game to track the new game
                            p1Score.value = 0
                            p2Score.value = 0
                            p1GameScoreString.value = "00"
                            p2GameScoreString.value = "00"

                        }
                    },
                    enabled = ((currGame < totalGames) && (p1Score.value != p2Score.value))
                ) {
                    Text("Next game")
                }
            }

            item {
                // Button to quit games
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (p1Score.value > p2Score.value) {
                            p1GameWinString += "$currGame"
                        } else {
                            p2GameWinString += "$currGame"
                        }
                        navController.navigate("GameNumSelectionScreen")
                    }
                ) {
                    Text("Quit")
                }
            }

        }

        LaunchedEffect(Unit) { focusRequester.requestFocus() }

    }
}


// A composable that represents a row of score components
@Composable
fun ScoreRow(score: MutableState<Int>, scoreString: MutableState<String>) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // A button to decrease the score
        Button(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = {
            score.value--
            scoreString.value = computeScore(score.value)
        },
        enabled = score.value > 0) {
            Text("-")
        }
        // A text box to show the score
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = scoreString.value,
            style = MaterialTheme.typography.body1,
            fontSize = 35.sp
        )
        // A button to increase the score
        Button(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = {
            score.value++
            scoreString.value = computeScore(score.value)
        },
        enabled = score.value < 5) {
            Text("+")
        }

    }
}

fun computeScore(score: Int): String {

    Log.i("score", "$score")
    return when (score) {
        0 -> "00"
        1 -> "15"
        2 -> "30"
        3 -> "40"
        4 -> "AD"
        5 -> "!!"
        else -> {
            "NA"
        }
    }

}

//@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
//@Composable
//fun GameScoreScreenPreview() {
//    GameScoreScreen(totalGames = 7)
//}