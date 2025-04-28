import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class DragAndDropViewModel : ViewModel() {
    val items = mutableStateListOf(
        mutableStateListOf("Item 1-1", "Item 1-2", "Item 1-3"),
        mutableStateListOf("Item 2-1", "Item 2-2", "Item 2-3"),
        mutableStateListOf("Item 3-1", "Item 3-2", "Item 3-3")
    )

    fun moveItem(draggedItem: String, fromPage: Int, toPage: Int) {
        if (fromPage != toPage) {
            items[fromPage].remove(draggedItem)
            items[toPage].add(draggedItem)
        }
    }
}



@Composable
fun DragAndDropPager4() {
    val viewModel: DragAndDropViewModel = viewModel()
    val pagerState = rememberPagerState(pageCount = {viewModel.items.size})

    HorizontalPager(state = pagerState, contentPadding = PaddingValues(end = 100.dp)) { page ->
        LazyColumn {
            items(viewModel.items[page]) { item ->
                DraggableItem(item = item, onDragEnd = { draggedItem ->
                    viewModel.moveItem(draggedItem, page, pagerState.currentPage)
                })
            }
        }
    }
}



@Composable
fun DraggableItem(item: String, onDragEnd: (String) -> Unit) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Surface(
        modifier = Modifier
            .offset(offsetX.dp, offsetY.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
    ) {
        Text(text = item)
    }
}













