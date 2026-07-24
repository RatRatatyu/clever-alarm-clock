package com.example.cleveralarmclock.presentation.mainScreenFeature.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CardScheduledAlarm(
    modifier: Modifier = Modifier,
    hour: String,
    minute: String,
    isActive: Boolean,
    onChanged: () -> Unit,
    onClick: ()-> Unit,
    onLongClick: () -> Unit,
    isSelectionMode: Boolean,
    isChecked: Boolean
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        colors = CardDefaults.cardColors(
            containerColor = if(isActive) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant
                },
        ),
    ) {
        Row (
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){

            Column(
                Modifier.weight(2f)
            ) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(2f)
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "$hour:$minute",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }


                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Mon-Fri") //will change to real data later
                }
            }

            Column(
                Modifier
                    .weight(1f)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                if(isSelectionMode){
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { onClick() },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Gray,
                            uncheckedColor = Color.Gray,
                            checkmarkColor = Color.White,
                            disabledCheckedColor = Color.LightGray,
                            disabledUncheckedColor = Color.LightGray
                        )
                    )
                }
                else{
                    Switch(
                        checked = isActive,
                        onCheckedChange = { _ -> onChanged() },
                        colors = SwitchDefaults.colors(

                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color.Gray,
                            checkedBorderColor = Color.Transparent,

                            uncheckedThumbColor = Color.Gray,
                            uncheckedTrackColor = Color.White,
                            uncheckedBorderColor = Color.Gray
                        )
                    )
                }
            }
        }
    }
}


//@Preview(showSystemUi = true)
//@Composable
//fun CardScheduledAlarmPrev(){
//    MaterialTheme{
//        Column (
//            Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ){
//            CardScheduledAlarm(hour = "12", minute = "00", isActive = false, onChanged = {}, onClick = {}, isSelectionMode = false)
//        }
//    }
//}