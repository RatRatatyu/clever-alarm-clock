package com.example.cleveralarmclock.presentation.mainScreenFeature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cleveralarmclock.presentation.mainScreenFeature.data.MainViewModel


@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val listForTest = arrayListOf("item1", "item2")
    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(listForTest) { item ->

                    Box(
                        modifier = Modifier
                            .padding(12.dp)
                            .background(Color.Blue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            item,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                    }
                }
            }

            Button(
                onClick = { viewModel.startAlarm() },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text("start alarm")
            }

            Button(
                onClick = { viewModel.cancelAlarm() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text("stop alarm")
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPrev() {
    MaterialTheme{
        MainScreen()
    }

}