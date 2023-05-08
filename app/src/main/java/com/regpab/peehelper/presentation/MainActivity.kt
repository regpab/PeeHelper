package com.regpab.peehelper.presentation

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.regpab.peehelper.data.PeePredictServiceV5
import com.regpab.peehelper.domain.PeePredictService
import com.regpab.peehelper.domain.UrinalState
import com.regpab.peehelper.domain.Urinals
import com.regpab.peehelper.presentation.ui.theme.PeeHelperTheme

class MainActivity : ComponentActivity() {

    private val peePredictService: PeePredictService = PeePredictServiceV5()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PeeHelperTheme {
                val activity = (LocalContext.current as Activity)
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            var urinals by remember {
                                mutableStateOf(
                                    Urinals(
                                        listOf(
                                            UrinalState.FREE,
                                            UrinalState.FREE,
                                            UrinalState.FREE,
                                            UrinalState.FREE,
                                            UrinalState.FREE,
                                        )
                                    )
                                )
                            }

                            fun updateState(value: UrinalState, index: Int) {
                                val newList = urinals.states.mapIndexed { itIndex, it ->
                                    if (index == itIndex) {
                                        value
                                    } else {
                                        it
                                    }
                                }
                                urinals = urinals.copy(states = newList)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = {
                                    if (urinals.states.size > 1) {
                                        urinals =
                                            urinals.copy(
                                                states = urinals.states.subList(
                                                    0,
                                                    urinals.states.size - 1
                                                )
                                            )
                                    }
                                }) {
                                    Text(
                                        text = "-",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 24.sp
                                    )
                                }
                                Text(
                                    text = urinals.states.size.toString(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 28.sp
                                )
                                IconButton(onClick = {
                                    val newList = ArrayList(urinals.states)
                                    newList.add(UrinalState.FREE)
                                    urinals = urinals.copy(states = newList)
                                }) {
                                    Text(
                                        text = "+",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 24.sp
                                    )
                                }
                            }
                            LazyRow(state = rememberLazyListState()) {
                                items(urinals.states.size) {
                                    UrinalWidget(isActive = urinals.states[it] == UrinalState.BUSY) {
                                        if (urinals.states[it] == UrinalState.BUSY) {
                                            updateState(UrinalState.FREE, it)
                                        } else {
                                            updateState(UrinalState.BUSY, it)
                                        }
                                    }
                                }
                            }
                            Button(onClick = {
                                val position = peePredictService.predict(urinals)
                                updateState(UrinalState.BUSY, position)
                            }) {
                                Text(text = "Choose position")
                            }
                            OutlinedButton(onClick = {
                                val size = urinals.states.size
                                val newList = ArrayList<UrinalState>()
                                repeat(size) {
                                    newList.add(UrinalState.FREE)
                                }
                                urinals = urinals.copy(states = newList)
                            }) {
                                Text(text = "Clear")
                            }
                        }
                    }
                }
            }
        }
    }
}
