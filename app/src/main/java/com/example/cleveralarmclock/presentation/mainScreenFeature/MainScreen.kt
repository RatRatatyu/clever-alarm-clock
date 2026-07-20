package com.example.cleveralarmclock.presentation.mainScreenFeature


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.room.util.TableInfo


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onAddAlarmClick: () -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
) {

    val scheduleList by viewModel.scheduleFlow.collectAsStateWithLifecycle(initialValue = emptyList())

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddAlarmClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить будильник"
                )
            }
        }
    ) { innerPadding ->
        LazyColumn (
            modifier = Modifier
                    .fillMaxWidth()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if(scheduleList.isEmpty()){
                item {
                    Text(
                        "You have no alarm clocks yet. Press + to add.",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }else{
                items(scheduleList, key= {it.id} ){ alarm ->
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                            .background(Color(alarm.colorHex.toColorInt()))
                            .clickable(onClick = onAddAlarmClick),

                    ){
                        Row (
                            Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text("${"%02d".format(alarm.hours)} : ${"%02d".format(alarm.minutes)}", style = MaterialTheme.typography.titleLarge)
                            Text("${alarm.id}")
                            Switch(checked = alarm.isActivate, onCheckedChange = { /* TODO: Toggle alarm status in ViewModel */})
                        }
                    }
                }
            }
        }
    }
}
