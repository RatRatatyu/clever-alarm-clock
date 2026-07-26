package com.example.cleveralarmclock.presentation.mainScreenFeature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cleveralarmclock.R
import com.example.cleveralarmclock.presentation.mainScreenFeature.components.AlarmList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onAddAlarmClick: (Int) -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
) {

    val scheduleList by viewModel.scheduleFlow.collectAsStateWithLifecycle()
    val nextAlarmText by viewModel.nextAlarmTime.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { alarmId ->
            onAddAlarmClick(alarmId)
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            if (uiState.isSelectedMode) {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { viewModel.clearSelection() }) {
                            Icon(Icons.Default.Close, contentDescription = stringResource(R.string.cancel))
                        }
                    },
                    actions = {
                        IconButton(onClick = { viewModel.getAllChecked() }) {
                            Icon(Icons.Default.Check, contentDescription = stringResource(R.string.select_all))
                        }
                    }
                )
            }
        },
        bottomBar = {
            if(uiState.isSelectedMode){
                BottomAppBar {
                    Spacer(modifier = Modifier.weight(1f))

                    FilledIconButton(
                        onClick = { viewModel.deleteAlarms() },
                        modifier = Modifier.size(56.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete),
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        },
        floatingActionButton = {
            if(!uiState.isSelectedMode){
                FloatingActionButton(
                    onClick = { onAddAlarmClick(-1) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add alarm clock"
                    )
                }
            }
        }
    ) { innerPadding ->

        Column (
            modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center
        ){
            Box(
                Modifier
                    .fillMaxSize()
                    .weight(1f)
            ){
                AlarmList(
                    scheduleList = scheduleList,
                    nextAlarmText = nextAlarmText,
                    isSelectedMode = uiState.isSelectedMode,
                    isChecked = uiState.selectedList,
                    toggleAlarmStatus = {alarmModel -> viewModel.toggleAlarmStatus(alarmModel)},
                    onPress = {alarmEntity -> viewModel.onPress(alarmEntity)},
                    onLongPress = {alarmEntity -> viewModel.onLongPress(alarmEntity)}
                )
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