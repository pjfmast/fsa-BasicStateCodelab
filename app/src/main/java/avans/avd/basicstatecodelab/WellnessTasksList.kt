package avans.avd.basicstatecodelab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@Composable
fun WelllnessTasksList(
    list: List<WellnessTask>,
    onCheckedTask: (WellnessTask, Boolean) -> Unit,
    onCloseTask: (WellnessTask) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        val coroutineScope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton by remember {
            derivedStateOf {
                //Here we create a condition if the firstVisibleItemIndex is greater than 0
                listState.firstVisibleItemIndex > 0
            }
        }

        LazyColumn(
            state = listState
        ) {
            items(
                items = list,
                key = { task -> task.id }
            ) { task ->
                WellnessTaskItem(taskName = task.label,
                    checked = task.checked,
                    onCheckedChange = { checked -> onCheckedTask(task, checked) },
                    onClose = { onCloseTask(task) }
                )
            }
        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            GoToTop() {
                coroutineScope.launch {
                    // Animate scroll to the first item
                    listState.animateScrollToItem(index = 0)
                }
            }
        }
    }
}

@Composable
fun GoToTop(
    modifier: Modifier = Modifier,
    goToTop: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier
            .padding(16.dp)
            .size(50.dp),
        onClick = goToTop,
        containerColor = White, contentColor = Black
    ) {
        Icon(Icons.Default.KeyboardArrowUp, contentDescription = "go to top")
    }
}
