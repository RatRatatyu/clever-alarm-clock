package com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel



//this composable will be used to display a wake up task
//but now we used it for test

@Composable
fun CameraTaskFeature(
    modifier: Modifier = Modifier,
    viewModel: CameraTaskViewModel = hiltViewModel()
){
    Scaffold (
        modifier
            .fillMaxSize()
    ){ innerPadding ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("Wake up ${viewModel.alarmId}")
            Button(onClick = { viewModel.stopMusic() }) { Text("Stop") }
        }

    }
}