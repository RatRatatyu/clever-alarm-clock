package com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.cameraTaskFeature

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.cameraTaskFeature.components.CameraViewComponent
import com.example.cleveralarmclock.ui.theme.CleverAlarmClockTheme

@Composable
fun CameraTaskScreen(
    viewModel: CameraTaskViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CameraTaskComponent(
        uiState = uiState,
        onPhotoTaken = {photo -> viewModel.onTakePhoto(photo)},
        onClear = {viewModel.onClearPhoto()},
        stopMusic = {viewModel.stopMusic()}
    )
}


@Composable
fun CameraTaskComponent(
    modifier: Modifier = Modifier,
    uiState: CameraTaskUiState,
    onPhotoTaken: (Bitmap?) -> Unit,
    onClear: () -> Unit,
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
            //for developing process
            //Button(onClick = { stopMusic() }) { Text("stop") }

            Card(
                Modifier.padding(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            ) {
                Text(
                    text = "Take photo of ${uiState.target}",
                    modifier = Modifier.padding(20.dp)
                )
            }


            CameraViewComponent(
                latestPhoto = uiState.lastTakenPhoto,
                onTakePhoto = {photo -> onPhotoTaken(photo)},
                onClearPhoto = onClear,
                isLoading = uiState.isLoading
            )
        }

    }

}

@Preview(showBackground = true, name = "Normal State")
@Composable
fun CameraTaskComponentPreview() {
    MaterialTheme(darkColorScheme()){
        CameraTaskComponent(
            uiState = CameraTaskUiState(
                isLoading = false,
                target = "Mug"
            ),
            onPhotoTaken = {},
            onClear = {},
            stopMusic = {}
        )
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
fun CameraTaskComponentLoadingPreview() {
    MaterialTheme(darkColorScheme()){
        CameraTaskComponent(
            uiState = CameraTaskUiState(
                isLoading = true,
                target = "Mug"
            ),
            onPhotoTaken = {},
            onClear = {},
            stopMusic = {}
        )
    }
}


