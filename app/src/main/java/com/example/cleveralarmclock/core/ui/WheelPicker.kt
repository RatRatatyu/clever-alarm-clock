package com.example.cleveralarmclock.core.ui

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WheelPicker(modifier: Modifier = Modifier){

    val minListTest = remember {
        List(61) { index -> "%02d".format(index) }
    }

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = 0)
    val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    Box(modifier.fillMaxSize()){
        LazyColumn(
            state = listState,
            flingBehavior = snapFlingBehavior,
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(minListTest.size) { index ->
                Text(
                    text = minListTest[index], // Use the formatted "00" string instead of just the index
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 8.dp) // Add some space between numbers
                )
            }
        }
    }
}

