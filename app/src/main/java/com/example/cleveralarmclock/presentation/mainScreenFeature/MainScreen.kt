package com.example.cleveralarmclock.presentation.mainScreenFeature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val listForTest = arrayListOf("item1", "item2")
    Scaffold (
        modifier = modifier
            .fillMaxSize()
    ){ innerPadding->
        LazyColumn (
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            items(listForTest){item ->
                Box (
                    modifier = modifier
                        .padding(all = 12.dp)
                        .background(Color.Blue),
                    contentAlignment = Alignment.Center
                ){
                    Text(item, style = MaterialTheme.typography.titleLarge, color = Color.White)
                }
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