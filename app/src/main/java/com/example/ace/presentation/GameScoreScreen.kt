package com.example.ace.presentation

import android.util.Log
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.CurvedTextStyle
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
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
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import kotlinx.coroutines.launch

//@OptIn(ExperimentalComposeUiApi::class)
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

    // P1 string will update to show the games won
    var p1String by remember {
        mutableStateOf("P1")
    }

    // P2 string will update to show the games won
    var p2String by remember {
        mutableStateOf("P2")
    }

    var winState: Int

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
                    text = p1String,
                    style = MaterialTheme.typography.title3
                )
            }

            item {
                ScoreRow(score = p1Score, scoreString = p1GameScoreString)
            }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = p2String,
                    style = MaterialTheme.typography.title3
                )
            }

            item {
                ScoreRow(score = p2Score, scoreString = p2GameScoreString)
            }

            item {
                Row() {

                    // Do not show the stop button if you are already at last game
                    if (currGame != totalGames) {
                        StopButton(
                            onClick = {
                                winState = if (p1String.length == p2String.length) {
                                    0
                                } else if (p1String.length > p2String.length) {
                                    1
                                } else if (p1String.length < p2String.length) {
                                    2
                                } else {
                                    3
                                }

                                navController.navigate("GameCompleteScreen/$winState")
                            })
                    }

                    NextButton(
                        onClick = {
                            Log.i("Curr game scores: ", "P1: ${p1Score.value} | P2: ${p2Score.value}")

                            if (currGame <= totalGames) {

                                if (p1Score.value > p2Score.value) {
                                    p1String += if (p1String == "P1") {
                                        " | $currGame"
                                    } else {
                                        ", $currGame"
                                    }
                                } else {
                                    p2String += if (p2String == "P2") {
                                        " | $currGame"
                                    } else {
                                        ", $currGame"
                                    }
                                }


                                // Reset the game to track the new game
                                p1Score.value = 0
                                p2Score.value = 0
                                p1GameScoreString.value = "00"
                                p2GameScoreString.value = "00"

                                if (currGame == totalGames) {

                                    winState = if (p1String.length == p2String.length) {
                                        0
                                    } else if (p1String.length > p2String.length) {
                                        1
                                    } else if (p1String.length < p2String.length) {
                                        2
                                    } else {
                                        3
                                    }

                                    navController.navigate("GameCompleteScreen/$winState")
                                }

                                currGame++
                            }
                        },
                        enabled = p1Score.value != p2Score.value
                    )

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

        SubtractButton(
            onClick = {
                score.value--
                scoreString.value = computeScore(score.value)
            },
            enabled = score.value > 0
        )

        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = scoreString.value,
            style = MaterialTheme.typography.display3,
        )

        AddButton(
            onClick = {
                score.value++
                scoreString.value = computeScore(score.value)
            },
            enabled = score.value < 5
        )

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

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun GameScoreScreenPreview() {
    GameScoreScreen(navController = rememberSwipeDismissableNavController(), totalGames = 7)
}