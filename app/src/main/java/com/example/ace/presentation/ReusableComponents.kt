package com.example.ace.presentation

import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonColors
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Picker
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberPickerState
import com.example.ace.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

val addSubButtonModifier = Modifier.size(ButtonDefaults.ExtraSmallButtonSize)
val defaultButtonModifier = Modifier.size(ButtonDefaults.SmallButtonSize)

@Composable
fun AceButton(
    modifier: Modifier = defaultButtonModifier,
    boxScope: @Composable BoxScope.() -> Unit,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.primaryButtonColors(),
    enabled: Boolean = true) {
    Button(
        modifier = modifier,
        onClick  = onClick,
        enabled = enabled,
        colors = colors,
        content = boxScope
    )
}

@Composable
fun AddButton(onClick: () -> Unit, enabled: Boolean) {
    AceButton(
        boxScope = {
         Icon(
             imageVector = Icons.Outlined.Add,
             contentDescription = stringResource(id = R.string.content_description_add),
         )
        },
        modifier = addSubButtonModifier,
        onClick = onClick,
        enabled = enabled
    )
}

@Composable
fun SubtractButton(onClick: () -> Unit, enabled: Boolean) {
    AceButton(
        boxScope = {
            Icon(
                imageVector = Icons.Outlined.Remove,
                contentDescription = stringResource(id = R.string.content_description_subtract)
            )
        },
        modifier = addSubButtonModifier,
        onClick = onClick,
        enabled = enabled
    )
}


@Composable
fun NextButton(onClick: () -> Unit, enabled: Boolean) {
    AceButton(
        boxScope = {
            Icon(
                imageVector = Icons.Outlined.ArrowForward,
                contentDescription = stringResource(id = R.string.content_description_next)
            )
        },
        onClick = onClick,
        enabled = enabled
    )
}

@Composable
fun AcceptButton(onClick: () -> Unit) {
    AceButton(
        boxScope = {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = stringResource(id = R.string.content_description_next)
            )
        },
        onClick = onClick,
    )
}


@Composable
fun StopButton(onClick: () -> Unit, enabled: Boolean = true) {
    AceButton(
        boxScope = {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = stringResource(id = R.string.content_description_stop)
            )
        },
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.error,
            contentColor = MaterialTheme.colors.onError
        ),
        enabled = enabled
    )
}


@Composable
fun gameNumPicker(): Int {

    val gameNumList = Array(99) { i -> i + 1 }
    val focusRequester = remember { FocusRequester() }
    val state = rememberPickerState(gameNumList.size)
    val contentDescription by remember {
        derivedStateOf {
            "${state.selectedOption + 1}"
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        val coroutineScope = rememberCoroutineScope()

        Picker(
            modifier = Modifier
                .size(100.dp, 100.dp)
                .onRotaryScrollEvent {
                    coroutineScope.launch {
                        state.scrollBy(it.verticalScrollPixels)
                    }
                    true
                }
                .focusRequester(focusRequester)
                .focusable(),
            separation = (-5).dp,
            state = state,
            contentDescription = contentDescription,
        ) {
            Text(
                text = gameNumList[it].toString(),
                style = MaterialTheme.typography.display1
            )
        }

        LaunchedEffect(Unit) { focusRequester.requestFocus() }

    }

    return state.selectedOption+1

}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun GameNumPickerPreview() {
    gameNumPicker()
}