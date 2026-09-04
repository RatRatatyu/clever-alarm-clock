package com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.shakeTaskFeature.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ShakeProgressIndicator(
    modifier: Modifier = Modifier,
    currentProgress: Float
) {
    val animatedProgress by animateFloatAsState(
        targetValue = currentProgress,
        label = "shake_progress_animation"
    )

    val surfaceColor = MaterialTheme.colorScheme.inverseSurface
    val primaryColor = MaterialTheme.colorScheme.onPrimary
    val strokeWidth = 20.dp

    Canvas(modifier.fillMaxSize()) {
        val strokeWidthPx = strokeWidth.toPx()

        drawCircle(
            color = surfaceColor,
            style = Stroke(width = strokeWidthPx)
        )

        drawArc(
            color = primaryColor,
            startAngle = -90f,
            sweepAngle = 360f * animatedProgress,
            useCenter = false,
            style = Stroke(
                width = strokeWidthPx,
                cap = StrokeCap.Round
            )
        )
    }
}