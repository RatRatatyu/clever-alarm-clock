package com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.shakeTaskFeature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cleveralarmclock.R
import com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.shakeTaskFeature.components.ShakeProgressIndicator


@Composable
fun ShakeTaskScreen(
    viewModel: ShakeTaskViewModel = hiltViewModel()
){
    val currentProgress by viewModel.progress.collectAsStateWithLifecycle()
    val isFinished by viewModel.isTaskFinished.collectAsStateWithLifecycle()

    ShakeTaskComponent(
        currentProgress = currentProgress,
        isTaskFinished = isFinished,
        stopMusic = {viewModel.stopMusic()}
    )
}


@Composable
fun ShakeTaskComponent(
    modifier: Modifier = Modifier,
    isTaskFinished: Boolean,
    currentProgress: Float,
    stopMusic: () -> Unit
){

    val brushGradient = Brush.radialGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.inverseSurface
        )
    )
    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding ->

        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(brushGradient)
        ){
            Column(
                Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.sizeIn(maxWidth = 300.dp, maxHeight = 300.dp),
                    contentAlignment = Alignment.Center
                ){

                    ShakeProgressIndicator(currentProgress = currentProgress)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "${(currentProgress * 100).toInt()}%",
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            style = MaterialTheme.typography.titleMedium,
                        )

                        Icon(
                            painter = painterResource(
                                id = if(isTaskFinished) R.drawable.outline_mobile_check else R.drawable.outline_mobile_vibrate
                            ),
                            contentDescription = "Shake phone",
                            modifier = Modifier
                                .size(90.dp)
                                .padding(vertical = 10.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )

                        Text(
                            text = if(isTaskFinished) "Well done!" else "Shake your phone!",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }

                //for developer process
                //Button(onClick = stopMusic, modifier = Modifier.padding(top= 30.dp) ) { Text("stop") }
            }
        }
    }
}


@Preview
@Composable
fun ShakeTaskPreview(){
    MaterialTheme{
        ShakeTaskComponent(
            currentProgress = 1.0f,
            isTaskFinished = true,
            stopMusic = {}
        )
    }
}
