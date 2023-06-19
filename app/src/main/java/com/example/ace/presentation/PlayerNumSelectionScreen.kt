package com.example.ace.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import com.example.ace.presentation.theme.AceTheme

@Composable
fun PlayerNumSelectionScreen() {
    AceTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center

        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { /*TODO*/ }) {
                Text("Singles")
            }
            Spacer(modifier = Modifier
                                .height(4.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { /*TODO*/ }) {
                Text("Doubles")
            }
        }
    }
}
@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun PlayerNumSelectionScreenPreview() {
    PlayerNumSelectionScreen()
}
