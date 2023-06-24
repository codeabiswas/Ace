package com.example.ace.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.SportsTennis
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController

@Composable
fun GameCompleteScreen (navController: NavController, winState: Int) {

    val contentString: String

    if (winState == 0) {
        contentString = "Draw"
    } else if (winState == 1) {
        contentString = "P1"
    } else if (winState == 2) {
        contentString = "P2"
    } else {
        contentString = "N/A"
    }

    CircularProgressIndicator(
        progress = 1.0f,
        modifier = Modifier.fillMaxSize(),
        startAngle = 0f,
        endAngle = 360f,
        strokeWidth = 6.dp
    )

    LazyColumn(
          modifier = Modifier
              .fillMaxSize(),
          verticalArrangement = Arrangement.Center
    ) {


        item {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {

                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(ButtonDefaults.LargeIconSize),
                    imageVector = Icons.Rounded.EmojiEvents,
                    contentDescription = "Winner"
                )
            }
        }
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = contentString,
                style = MaterialTheme.typography.display2
            )
        }

        item {

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                AceChip(
                    modifier = Modifier.align(Alignment.Center),
                    label = "New match",
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.SportsTennis,
                            contentDescription = "New match"
                        )
                    },
                    onClick = {
                        navController.navigate("GameNumSelectionScreen")
                    }
                )
            }

        }



//  Spacer(modifier = Modifier.height(8.dp))


    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun GameCompleteScreenPreview() {
    GameCompleteScreen(navController = rememberSwipeDismissableNavController(), winState = 0)
}
