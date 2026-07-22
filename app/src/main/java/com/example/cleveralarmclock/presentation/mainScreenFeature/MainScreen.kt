package com.example.cleveralarmclock.presentation.mainScreenFeature


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cleveralarmclock.presentation.mainScreenFeature.components.CardScheduledAlarm


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onAddAlarmClick: () -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
) {

    val scheduleList by viewModel.scheduleFlow.collectAsStateWithLifecycle()
    val nextAlarmText by viewModel.nextAlarmTime.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddAlarmClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add alarm clock"
                )
            }
        }
    ) { innerPadding ->
        if(scheduleList.isEmpty()){
            Column (
                modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){ Text(
                "You have no alarm clocks yet. Press + to add.",
                style = MaterialTheme.typography.titleMedium
            ) }
        }else{

            Column (

            ){
                nextAlarmText?.let { text ->
                    Text(
                        text = text,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                LazyColumn (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    items(scheduleList, key= {it.id} ){ alarm ->
                        CardScheduledAlarm(
                            Modifier,
                            hour = "%02d".format(alarm.hours),
                            minute = "%02d".format(alarm.minutes),
                            isActive = alarm.isActivate,
                            onChanged = {viewModel.toggleAlarmStatus(alarm)}

                        )
                    }
                }
            }

        }

    }
}


