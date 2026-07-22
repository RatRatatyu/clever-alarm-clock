package com.example.cleveralarmclock.presentation.mainScreenFeature

import java.util.Locale
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cleveralarmclock.R
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
                stringResource(R.string.you_have_no_alarm_clocks_yet_press_to_add),
                style = MaterialTheme.typography.titleMedium
            ) }
        }else{
            Column (
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                nextAlarmText?.let { text ->
                    Text(
                        text = text,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }

                LazyColumn (
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    items(scheduleList, key= {it.id} ){ alarm ->
                        CardScheduledAlarm(
                            Modifier,

                            hour = String.format(Locale.getDefault(), "%02d", alarm.hours),
                            minute = String.format(Locale.getDefault(), "%02d", alarm.minutes),
                            isActive = alarm.isActivate,
                            onChanged = {viewModel.toggleAlarmStatus(alarm)}

                        )
                    }
                }
            }

        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPrev(){
    MaterialTheme{
        MainScreen(
            onAddAlarmClick = {},
            viewModel = viewModel()
        )
    }
}