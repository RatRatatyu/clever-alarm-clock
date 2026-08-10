package com.example.cleveralarmclock.presentation.mainScreenFeature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PHONE
import androidx.compose.ui.tooling.preview.Devices.TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cleveralarmclock.R
import com.example.cleveralarmclock.presentation.mainScreenFeature.components.AlarmList
import java.time.DayOfWeek


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onAddAlarmClick: (Int) -> Unit,
    windowSizeClass: WindowSizeClass,
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

    when(windowSizeClass.widthSizeClass){
        WindowWidthSizeClass.Compact -> MainScreenCompact(
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

        WindowWidthSizeClass.Medium,
        WindowWidthSizeClass.Expanded -> MainScreenExpanded(
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
}


@Composable
fun MainScreenExpanded(
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
                TopAppBarComponent(
                    clearSelection = clearSelection,
                    getAllChecked = getAllChecked
                )
            }
        },
    ) { innerPadding ->

        Row (
            modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalArrangement = Arrangement.Center
        ){
            Column(
                modifier = Modifier
                    .weight(2f)
            ) {
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
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if(uiState.isSelectedMode){
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
                }else{
                    FilledIconButton(
                        onClick = { onAddAlarmClick(-1) },
                        modifier = Modifier.size(56.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add alarm clock"
                        )
                    }
                }


            }


        }
    }
}

@Composable
fun MainScreenCompact(
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
                TopAppBarComponent(
                    clearSelection = clearSelection,
                    getAllChecked = getAllChecked
                )
            }
        },
        bottomBar = {
            if(uiState.isSelectedMode){
                BottomBarComponent ( deleteAlarms = deleteAlarms )
            }
        },
        floatingActionButton = {
            if(!uiState.isSelectedMode){
                FloatingActionButtonComponent (onAddAlarmClick = onAddAlarmClick)
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

@Composable
fun FloatingActionButtonComponent(
    onAddAlarmClick: (Int) -> Unit
){
    FloatingActionButton(
        onClick = { onAddAlarmClick(-1) }
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add alarm clock"
        )
    }
}
@Composable
fun BottomBarComponent(
    deleteAlarms: () -> Unit
){
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(
    clearSelection: () -> Unit,
    getAllChecked: () -> Unit
){
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

@Preview(showSystemUi = true, device = TABLET)
@Preview(showSystemUi = true, device = PHONE)
@Composable
fun MainScreenPrev() {

    val previewAlarms = listOf(
        AlarmUiModel(id = 1, timeFormatted = "07:00", isActivated =  true,  repeatedDays = setOf(
            DayOfWeek.WEDNESDAY, DayOfWeek.MONDAY)),
        AlarmUiModel(id = 2, timeFormatted = "09:30", isActivated = false, repeatedDays = setOf())
    )
    MaterialTheme {
        MainScreenCompact(
            scheduleList = previewAlarms,
            nextAlarmText = "Прозвенит через 8 ч 30 мин",
            uiState = MainState(
                isSelectedMode = false,
                selectedList = listOf(1)
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