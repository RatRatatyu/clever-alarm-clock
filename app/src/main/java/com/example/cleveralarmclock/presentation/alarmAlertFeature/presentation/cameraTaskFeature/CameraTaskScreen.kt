package com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.cameraTaskFeature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CameraTaskScreen(
    viewModel: CameraTaskViewModel = hiltViewModel()
){
    CameraTaskComponent(stopMusic = {viewModel.stopMusic()})
}


@Composable
fun CameraTaskComponent(
    modifier: Modifier = Modifier,
    stopMusic: () -> Unit
){

    Scaffold(
        modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("This is screen task for CAMERA")
            Button(onClick = { stopMusic() }) { Text("stop") }
        }

    }

}


@Preview
@Composable
fun CameraTaskPreview(){
    MaterialTheme(darkColorScheme()){
        CameraTaskComponent(
            stopMusic = {}
        )
    }
}