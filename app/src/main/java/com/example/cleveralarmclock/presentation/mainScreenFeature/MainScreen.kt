package com.example.cleveralarmclock.presentation.mainScreenFeature


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cleveralarmclock.presentation.mainScreenFeature.data.MainViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val listForTest = arrayListOf("item1", "item2")


    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {viewModel.startAlarm()} //for test
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить будильник"
                )
            }
        }
    ) { innerPadding ->
        Column (
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            Button(
                onClick = { viewModel.stopAlarm() }
            ) { Text("Cancel Alarm Clock") }

            Button(
                onClick = { viewModel.stopAlarmService() }
            ) { Text("Stop Music and Server") }

        }
    }
}

