package com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.alarmRingFeature

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cleveralarmclock.R
import com.example.cleveralarmclock.core.domain.task.TaskType
import java.time.LocalDateTime
import java.util.Locale


@Composable
fun AlarmRingScreen(
    viewModel: AlarmRingViewModel = hiltViewModel(),
    onStopAlarm: (TaskType) -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { taskId ->
            onStopAlarm(taskId)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.closeActivityEvent.collect {
            activity?.finish()
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AlarmRingComponent(
        uiState = uiState,
        snoozeAlarm = {viewModel.snoozeAlarm()},
        onStopAlarm = {viewModel.onStopAlarm()}
    )
}



@Composable
fun AlarmRingComponent(
    modifier: Modifier = Modifier,
    uiState: AlarmRingUiState,
    snoozeAlarm: () -> Unit,
    onStopAlarm: () -> Unit
){
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.inverseSurface
        )
    )

    val topContentColor = MaterialTheme.colorScheme.onPrimary

    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding ->

        val now = LocalDateTime.now()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
                .padding(innerPadding)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if(uiState.isLoading){
                    CircularProgressIndicator()
                }else{

                    // Current Time and Task name
                    Column(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.alarm),
                            style = MaterialTheme.typography.labelMedium,
                            color = topContentColor,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )

                        Text(
                            text = String.format(Locale.getDefault(), "%02d:%02d", now.hour, now.minute),
                            color = topContentColor,
                            style = MaterialTheme.typography.displayLarge.copy(fontSize = 100.sp),
                        )

                        uiState.taskName?.let { resId ->
                            Row(Modifier.padding(top = 10.dp)) {
                                Text(
                                    text = "Your task for today: ",
                                    color = topContentColor,
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                Text(
                                    text = stringResource(resId),
                                    color = topContentColor,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }

                    // Action Buttons
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp)
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { onStopAlarm() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Stop Alarm")
                        }

                        Button(
                            onClick = {snoozeAlarm()},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onSurface),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(R.string.snoozing_for_10_minutes))
                        }
                    }
                }
            }
        }

    }
}


@Preview(showSystemUi = true)
@Composable
fun AlarmRingScreenPreview(){
    MaterialTheme{
        AlarmRingComponent(
            uiState = AlarmRingUiState(
                isLoading = false,
                taskId = TaskType.SHAKE,
                taskName = R.string.shake
            ),
            onStopAlarm = {},
            snoozeAlarm = {}
        )
    }
}






