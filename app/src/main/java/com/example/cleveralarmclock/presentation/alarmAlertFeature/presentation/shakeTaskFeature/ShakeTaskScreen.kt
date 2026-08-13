package com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.shakeTaskFeature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun ShakeTaskScreen(
    modifier: Modifier = Modifier,
    viewModel: ShakeTaskViewModel = hiltViewModel()
){
    Column(
        modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("This is screen task for SHAKE")
        Button(onClick = { viewModel.stopMusic() }) { Text("stop") }
    }
}