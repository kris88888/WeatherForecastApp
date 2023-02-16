package com.examples.weatherforecast.practice

import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun DisposableEffectExample(viewModel: PracticeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var timerStartStop by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                timerStartStop = !timerStartStop
            }) {
                Text(if (timerStartStop) "Stop" else "Start")
            }
        }
    }

    val context = LocalContext.current

    DisposableEffect(key1 = timerStartStop) {
        val x = (1..10).random()
        Toast.makeText(context, "Start $x", LENGTH_SHORT).show()

        onDispose {
            Toast.makeText(context, "Stop $x", LENGTH_SHORT).show()
        }
    }
}


@Composable
fun CancelDetectLaunchedEffect() {

    var timer by remember { mutableStateOf(0) }
    var timerStartStop by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Time $timer")
            Button(onClick = { timerStartStop = !timerStartStop }) {
                Text(if (timerStartStop) "Stop" else "Start")
            }
        }
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = timerStartStop) {
        val x = (1..10).random()
        try {
            while (timerStartStop) {
                delay(2000)
                timer++
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(context, "Oh No $x", Toast.LENGTH_SHORT).show()
        } finally {
            Toast.makeText(context, "Done $x", Toast.LENGTH_SHORT).show()

        }
    }

}

@Composable
fun LaunchedSideEffectExample(viewModel: PracticeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    var timer by rememberSaveable { mutableStateOf(0) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Last Updated: $timer")
    }

    LaunchedEffect(key1 = timer) {
        while (timer < 50) {
            delay(1000)
            timer++
        }
    }
}

@Composable
fun SideEffectExample(
    viewModel: PracticeViewModel =
        androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val TAG = "SideEffectExample"
    Log.d(TAG, "Side Effect Recomposition Log")
    var timer by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Timer $timer")
    }

    SideEffect {
        Thread.sleep(1000)
        timer++
    }

    Thread.sleep(1000)
    timer++
}

@Composable
fun JustLaunchEffect() {
    var resetCounter by remember { mutableStateOf(0) }
    var timer by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Button(onClick = { resetCounter++ }) {
                Text("Reset Counter")
            }
            Text("Timer $timer")
        }
    }
    LaunchedEffect(key1 = resetCounter) {
        timer = 0
        while (true) {
            delay(1000)
            timer++
        }
    }
}

@Composable
fun produceStateExample() {
    // triggers recomposition.
    val timer by produceState(initialValue = 0) {
        while (value < 10) {
            delay(1000)
            value++
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text("Timer $timer")
        }
    }
}

@Composable
fun RememberCoroutineScopeExample() {
    val scope = rememberCoroutineScope()
    var timer by remember {
        mutableStateOf(0)
    }
    var timerStartStop by remember {
        mutableStateOf(false)
    }
    var job: Job? by remember {
        mutableStateOf(null)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                18.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Column {
            ExampleComponent(
                "Launched Effect would launch on first composition." +
                        "However, for situations, where we" +
                        "wish to have more control over when to trigger effect, \"Coroutine Scope\" is useful."
            )

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterHorizontally)
                    .padding(20.dp), text = "Timer $timer"
            )
            Button(
                onClick = {
                    timerStartStop = !timerStartStop
                    if (timerStartStop) {
                        job?.cancel()
                        job = scope.launch {
                            while (true) {
                                delay(1000)
                                timer++
                            }
                        }
                    } else {
                        job?.cancel()
                    }

                }, modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterHorizontally)
                    .padding(20.dp)
            ) {
                Text(if (timerStartStop) "Stop" else "Start")
            }
        }
    }
}

@Composable
fun ExampleComponent(message: String) {
    Text(
        modifier = Modifier
            .wrapContentSize()
            .padding(18.dp),
        text = message,
    )
}

@Composable
fun DerivedStateExample() {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val listState = rememberLazyListState()
        LazyColumn(state = listState) {
            items(100) { index ->
                Text(
                    text = "Item : $index", modifier = Modifier
                        .wrapContentSize()
                        .padding(20.dp)
                )
            }
        }
        val showButtonDerive by
        remember {
            derivedStateOf {
                listState.firstVisibleItemIndex > 0
            }
        }
        Log.d("KRIS", "Recompose")
        Column {
            AnimatedVisibility(showButtonDerive) {
                Button({}) {
                    Text("Row 1 Hiding")
                }
            }
        }
    }
}

@Composable
fun TestSnapshotFlow() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val listState = rememberLazyListState()

        LazyColumn(state = listState) {
            items(1000) { index ->
                Text(text = "Item: $index")
            }
        }

        var showButtonSnapshot by remember {
            mutableStateOf(false)
        }
        Log.d("Track", "Recompose")

        Column {
            AnimatedVisibility(showButtonSnapshot) {
                Button({}) {
                    Text("Row 1 and 2 hiding")
                }
            }
        }

        LaunchedEffect(key1 = listState) {
            // execute this launched effect.
            // emit the list state visible index flow/
            snapshotFlow {
                listState.firstVisibleItemIndex
            } // map the index to a boolean
                .map { index ->
                    index > 2
                } // only emit the changed values.
                .distinctUntilChanged()
                .collect {
                    showButtonSnapshot = it
                }
        }
    }
}



