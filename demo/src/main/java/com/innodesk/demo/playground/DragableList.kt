package com.innodesk.demo.playground

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.zIndex
import com.example.designsystem.component.dragContainer
import com.example.designsystem.component.draggableItems
import com.example.designsystem.component.rememberDragDropState
import kotlinx.coroutines.channels.Channel
import timber.log.Timber

@Composable
fun MyList() {
    var list1 by remember { mutableStateOf(List(20) { it }) }
    val list2 by remember { mutableStateOf(List(20) { it + 20 }) }
    val draggableItems by remember {
        derivedStateOf { list1.size }
    }

    val stateList = rememberLazyListState()

    val dragDropState =
        rememberDragDropState(
            lazyListState = stateList,
            draggableItemsNum = draggableItems,
            onMove = { fromIndex, toIndex ->
                list1 = list1.toMutableList().apply { add(toIndex, removeAt(fromIndex))}
            })



    LazyColumn(
        modifier = Modifier.dragContainer(dragDropState),
        state = stateList,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(text = "Title 1", fontSize = 30.sp)
        }

        draggableItems(items = list1, dragDropState = dragDropState) { modifier, item ,index ->
            Item(
                modifier = modifier.clickable {
                    Timber.tag("ASJZXJCPSAKLXZC").d("Drag Index : ${dragDropState.draggingItemIndex}")
                    Timber.tag("ASJZXJCPSAKLXZC").d("Delta : ${dragDropState.delta}")
                    Timber.tag("ASJZXJCPSAKLXZC").d("ScrollChannel : ${dragDropState.scrollChannel}")
                    list1.forEachIndexed { index, i ->
                        Timber.tag("LLKXMZNHHASDAS").d("Index : ${index} , Item: ${i}")
                    }
                },
                index = item,
            )

        }

        item {
            Text(text = "Title 2", fontSize = 30.sp)
        }

        itemsIndexed(list2, key = { _, item -> item }) { _, item ->
            Item(index = item)
        }

    }
}


@Composable
private fun Item(modifier: Modifier = Modifier, index: Int) {
    Card(
        modifier = modifier
    ) {
        Text(
            "Item $index",
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview(){
    MyList()
}











