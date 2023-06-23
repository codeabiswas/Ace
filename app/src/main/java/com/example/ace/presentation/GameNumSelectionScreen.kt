package com.example.ace.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.OutlinedChip
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListAnchorType
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.ace.R


@Composable
fun GameNumSelectionScreen (navController: NavController) {

    var totalGames = remember {
        mutableStateOf(1)
    }


    // Use ScalingLazyColumn to create a scrollable list of items that scale based on their position
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        item {
            Text(text = stringResource(id = R.string.ask_num_casual_games))
        }

        item { totalGames.value = gameNumPicker() }

        item {
            AcceptButton(
                onClick = {
                    Log.i("totalGames: ", "${totalGames.value}")
                    navController.navigate("GameScoreScreen/${totalGames.value}")
                },
            )
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

        // Subtract games
        SubtractButton(
            onClick = {
              gameNum.value--
            },
            enabled = gameNum.value > 1
        )

        // A text box to show the total games
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = "${gameNum.value}",
            style = MaterialTheme.typography.title1,
//            fontSize = 35.sp
        )

        // Add games
        AddButton(
            onClick = {
              gameNum.value++
            },
            enabled = gameNum.value < 11
        )

    }
}


@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun GameNumSelectionScreenPreview() {
    GameNumSelectionScreen(navController = rememberSwipeDismissableNavController())
}
