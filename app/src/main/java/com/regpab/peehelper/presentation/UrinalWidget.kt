package com.regpab.peehelper.presentation

import androidx.compose.foundation.Image
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.regpab.peehelper.R

@Composable
fun UrinalWidget(isActive: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Black.copy(
                alpha = 0.0F,
            ),
        ),
    ) {
        Image(
            painter = if (isActive) {
                painterResource(id = R.drawable.urinal_full)
            } else {
                painterResource(id = R.drawable.urinal_empty)
            }, contentDescription = if (isActive) "urinal full" else "urinal empty"
        )
    }
}