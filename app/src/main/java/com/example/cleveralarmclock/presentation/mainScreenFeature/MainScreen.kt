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

    MainScreenContent(
        modifier = modifier,
        scheduleList = scheduleList,
        nextAlarmText = nextAlarmText,
        uiState = uiState,
        onAddAlarmClick = onAddAlarmClick,
        clearSelection = { viewModel.clearSelection() },
        getAllChecked = { viewModel.getAllChecked() },
        deleteAlarms = { viewModel.deleteAlarms() },
        toggleAlarmStatus = { viewModel.toggleAlarmStatus(it) },
        onPress = { viewModel.onPress(it) },
        onLongPress = { viewModel.onLongPress(it) }
    )


}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    scheduleList: List<AlarmUiModel>,
    nextAlarmText: String?,
    uiState: MainState,
    onAddAlarmClick: (Int) -> Unit,
    clearSelection: () -> Unit,
    getAllChecked: () -> Unit,
    deleteAlarms: () -> Unit,
    toggleAlarmStatus: (Int) -> Unit,
    onPress: (Int) -> Unit,
    onLongPress: (Int) -> Unit
){
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            if (uiState.isSelectedMode) {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { clearSelection() }) {
                            Icon(Icons.Default.Close, contentDescription = stringResource(R.string.cancel))
                        }
                    },
                    actions = {
                        IconButton(onClick = { getAllChecked() }) {
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
                        onClick = { deleteAlarms() },
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
                    toggleAlarmStatus = {alarmId -> toggleAlarmStatus(alarmId)},
                    onPress = {alarmId -> onPress(alarmId)},
                    onLongPress = {alarmId -> onLongPress(alarmId)}
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPrev() {
    MaterialTheme {
        MainScreenContent(
            scheduleList = emptyList(),
            nextAlarmText = "Прозвенит через 8 ч 30 мин",
            uiState = MainState(
                isSelectedMode = false,
                selectedList = emptyList()
            ),
            onAddAlarmClick = {},
            clearSelection = {},
            getAllChecked = {},
            deleteAlarms = {},
            toggleAlarmStatus = {},
            onPress = {},
            onLongPress = {}
        )
    }
}