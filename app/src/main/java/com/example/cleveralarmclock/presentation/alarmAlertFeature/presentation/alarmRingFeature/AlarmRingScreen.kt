package com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.alarmRingFeature

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cleveralarmclock.R
import com.example.cleveralarmclock.core.domain.task.TaskType


@Composable
fun AlarmRingScreen(
    modifier: Modifier = Modifier,
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

    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if(uiState.isLoading){
                CircularProgressIndicator()
            }else{

                Button(onClick = {viewModel.snoozeAlarm()}) {
                    Text(stringResource(R.string.snoozing_for_10_minutes))
                }

                Button(onClick = { viewModel.onStopAlarm() }) {
                    Text("Stop Alarm")
                }
            }
        }

    }
}













