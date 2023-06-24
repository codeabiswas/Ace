package com.example.ace.presentation

import android.util.Log
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Leaderboard
import androidx.compose.material.icons.rounded.SportsTennis
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.foundation.CurvedTextStyle
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.TimeTextDefaults
import androidx.wear.compose.material.curvedText
import androidx.wear.compose.material.rememberScalingLazyListState
import androidx.wear.compose.material.scrollAway
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.ace.R
import kotlinx.coroutines.launch

/*
    **NOTE**

    The actual game score is tracked as states (Int). States map to scores (String).

    // State -> Score
    // 0 -> 00
    // 1 -> 15
    // 2 -> 30
    // 3 -> 40
    // 4 -> AD
    // 5 -> !! (Win)

 */

@Composable
fun GameScoreScreen (navController: NavController, totalGames: Int) {
    val leadingTextStyle = TimeTextDefaults.timeTextStyle(color = MaterialTheme.colors.primary)

    val listState = rememberLazyListState()

    var currGame by remember {
        mutableStateOf(1)
    }


    val p1State = remember {
        mutableStateOf(0)
    }

    val p2State = remember {
        mutableStateOf(0)
    }


    var p1WinCounter = 0
    var p2WinCounter = 0

    var winState: Int

    var serveSide = remember {
        mutableStateOf("R")
    }

    // Use Scaffold to get a curved text time at the top, a vignette, and a scrolling indicator
    Scaffold(
        timeText = { TimeText(
            modifier = Modifier.scrollAway(listState),
            startLinearContent = {
                Text(
                    text = "G: $currGame/$totalGames",
                    style = leadingTextStyle
                )
            },
            startCurvedContent = {
                curvedText(
                    text = "G: $currGame/$totalGames",
                    style = CurvedTextStyle(leadingTextStyle)
                )
            },
        ) },

//        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        positionIndicator = {
            PositionIndicator(
                lazyListState = listState
            )
        }
    ) {

        val focusRequester = remember { FocusRequester() }
        val coroutineScope = rememberCoroutineScope()


        // Use ScalingLazyColumn to create a scrollable list of items that scale based on their position
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .onRotaryScrollEvent {
                    coroutineScope.launch {
                        listState.scrollBy(it.verticalScrollPixels)
                    }
                    true
                }
                .focusRequester(focusRequester)
                .focusable()
            ,
            state = listState
        ) {
            item(key = 0) {

                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Column() {
                            PlayerTeamLabel(text = stringResource(R.string.player_one))
                            ScoreRow(p1State = p1State, p2State = p2State, mainState = p1State, serveSide = serveSide)
                        }

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = serveSide.value,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.caption1
                        )
//                        Spacer(Modifier.size(12.dp))

                        Column() {
                            ScoreRow(p1State = p1State, p2State = p2State, mainState = p2State, serveSide = serveSide)
                            PlayerTeamLabel(text = stringResource(R.string.player_two))
                        }

                    }
                }

            item(key = 1) {

                Column(
                    modifier = Modifier
                        .fillParentMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {


                    // Show set stats here
                    Column() {
                        Box(
                            modifier = Modifier
                                .size(ButtonDefaults.LargeIconSize)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Leaderboard,
                                contentDescription = "Casual set stats icon",
                                modifier = Modifier.size(ButtonDefaults.LargeIconSize)
                            )
                        }
                        Text(
                            text = "P1 wins: $p1WinCounter",
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "P2 wins: $p2WinCounter",
                            textAlign = TextAlign.Center
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceEvenly
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

//                        NextButton(
                        NextChip(
                            onClick = {
//                            Log.i("Curr game scores: ", "P1: ${p1State.value} | P2: ${p2State.value}")

                                if (currGame <= totalGames) {

                                    // Update win counter
                                    if (p1State.value > p2State.value) p1WinCounter++ else p2WinCounter++

                                    // Reset the game to track the new game
                                    p1State.value = 0
                                    p2State.value = 0
                                    serveSide.value = "R"


                                    if (currGame == totalGames) {

                                        winState = calculateWinner(p1WinCounter, p2WinCounter)

                                        navController.navigate("GameCompleteScreen/$winState")
                                    }

                                    currGame++
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(0)
                                    }
                                }
                            },
                            enabled = p1State.value != p2State.value
                        )

                        // Do not show the stop button if you are already at last game
                        if (currGame != totalGames) {
//                            StopButton(
                            QuitChip(
                                onClick = {

                                    winState = calculateWinner(p1WinCounter, p2WinCounter)

                                    navController.navigate("GameCompleteScreen/$winState")

                                })
                        }



                    }

                }

            }

        }

        LaunchedEffect(Unit) { focusRequester.requestFocus() }

    }
}

fun calculateWinner(p1Score: Int, p2Score: Int): Int {

    return if (p1Score == p2Score) {
        0
    } else if (p1Score > p2Score) {
        1
    } else if (p1Score < p2Score) {
        2
    } else {
        3
    }

}


// A composable that represents a row of score components
@Composable
fun ScoreRow(
    p1State: MutableState<Int>,
    p2State: MutableState<Int>,
    mainState: MutableState<Int>,
    serveSide: MutableState<String>
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        OutlineSubtractButton(
            onClick = {

                mainState.value--

                Log.i("p1Score", "${p1State.value}")
                Log.i("p2Score", "${p2State.value}")

                serveSide.value = if (serveSide.value == "R") "L" else "R"

            },
            enabled = (mainState.value > 0)
        )

        PlayerScoreLabel(text = translateScore(p1Score = p1State.value, p2Score = p2State.value, mainScore = mainState.value))

        OutlineAddButton(
            onClick = {

                // P1 score is AD and P2 score is 40
                if (p1State.value == 4 && p2State.value == 3) {
                    // P1 score is AD and the score called to update is P1's
                    if (p1State.value == 4 && mainState.value == 4) {
                        // P1 wins
                        mainState.value = 5
                    } else {
                        // Otherwise, P1 is back to deuce
                        p1State.value = 3
                    }

                // P2 score is AD and P1 score is 40
                } else if (p2State.value == 4 && p1State.value == 3) {
                    // P2 score is AD and the score called to update is P2's
                    if (p2State.value == 4 && mainState.value == 4) {
                        // P2 wins
                        mainState.value = 5
                    } else {
                        // Otherwise, P2 is back to deuce
                        p2State.value = 3
                    }
                // Otherwise, update specified score
                } else {
                    mainState.value++
                }

                serveSide.value = if (serveSide.value == "R") "L" else "R"

//                Log.i("p1Score", "${p1State.value}")
//                Log.i("p2Score", "${p2State.value}")

            },
            enabled = mainState.value < 5 && translateScore(p1Score = p1State.value, p2Score = p2State.value, mainScore = mainState.value) != "!!"
        )

    }
}


fun translateScore(p1Score: Int, p2Score: Int, mainScore: Int): String {

    // State -> Score
    // 0 -> 00
    // 1 -> 15
    // 2 -> 30
    // 3 -> 40
    // 4 -> AD
    // 5 -> !!

    return when (mainScore) {
        0 -> "00"
        1 -> "15"
        2 -> "30"
        3 -> "40"
        4 -> {
            // Only enter AD iff other player's score is 40
            if ((p1Score == 4 && p2Score == 3) || (p2Score == 4 && p1Score == 3)) {
                "AD"
            } else {
                "!!"
            }
        }
        5 -> "!!"
        else -> {
            "NA"
        }
    }

}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun GameScoreScreenPreview() {
    GameScoreScreen(navController = rememberSwipeDismissableNavController(), totalGames = 7)
}