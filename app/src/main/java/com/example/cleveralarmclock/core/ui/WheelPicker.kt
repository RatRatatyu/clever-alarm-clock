package com.example.cleveralarmclock.core.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.LocalTime
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WheelPicker(
    modifier: Modifier = Modifier,
    items: List<String>,
    initialItem: String,
    onItemSelected: (String) -> Unit,
    isInfinite: Boolean = true
) {
    val itemHeight = 45.dp
    val maxItems = if(isInfinite) Int.MAX_VALUE else items.size + 4

    val targetIndex = items.indexOf(initialItem).coerceAtLeast(0)

    val startIndex = if(isInfinite){
        //  since the height is exactly 5 elements,
        //  we subtract 2 so that the target item becomes exactly in the middle
        val baseCenterIndex = maxItems / 2 - (maxItems / 2 % items.size)
        baseCenterIndex + targetIndex - 2
    } else{
        targetIndex
    }

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = startIndex)
    val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val currentCenteredIndex = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (visibleItemsInfo.isEmpty()) return@derivedStateOf 0

            val containerCenter = layoutInfo.viewportEndOffset / 2
            visibleItemsInfo.minByOrNull {
                abs((it.offset + it.size / 2) - containerCenter)
            }?.index ?: 0
        }
    }

    LaunchedEffect(currentCenteredIndex.value) {
        snapshotFlow { currentCenteredIndex.value }
            .distinctUntilChanged()
            .collect { virtualIndex ->
                // Subtract 2 for the final list to adjust for empty spaces
                val realIndex = if(isInfinite) virtualIndex % items.size else virtualIndex - 2

                if (realIndex in items.indices) {
                    onItemSelected(items[realIndex])

                }
            }
    }

    Box(
        modifier = modifier.height(itemHeight * 5),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = listState,
            flingBehavior = snapFlingBehavior,
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(maxItems) { index ->
                val distance = abs(index - currentCenteredIndex.value)

                val rotationX = when(distance){
                    0 -> 0f
                    1 -> 25f
                    2 -> 45f
                    else -> 60f
                }

                val alphaValue = when(distance){
                    0 -> 1f
                    1 -> 0.5f
                    else -> 0.15f
                }

                val realIndex = if(isInfinite) index % items.size else index -2
                val isValidItem = realIndex in items.indices
                val item = if (isValidItem) items[realIndex] else ""

                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth()
                        .graphicsLayer{
                            this.rotationX = if(index > currentCenteredIndex.value ) -rotationX else rotationX
                            this.scaleX = if(distance ==0) 1.1f else 1.0f
                            this.scaleY = if(distance ==0) 1.1f else 1.0f
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.alpha(alphaValue)
                    )
                }
            }
        }

    }
}