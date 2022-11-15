package tech.dzolotov.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel
import tech.dzolotov.todolist.repository.ITodoRepository
import tech.dzolotov.todolist.repository.Task
import tech.dzolotov.todolist.ui.theme.TodolistTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodolistTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoListApp()
                }
            }
        }
    }
}

@Composable
fun TodoListApp() {
    val viewModel = getViewModel<MainViewModel>()
    LaunchedEffect(Unit) {
        viewModel.loadData()
    }
    Column {
        Text(
            "Todo List",
            modifier = Modifier
                .semantics {
                    contentDescription = "Header"
                    testTag = "header"
                }
                .padding(bottom = 12.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 32.sp,

            )
        TaskList()
    }
}

@Composable
fun TaskList() {
    val repository = get<ITodoRepository>()
    val tasks by repository.getState().collectAsState()
    LazyColumn {
        this.items(tasks) {
            TaskComposable(it)
        }
        item {
            NewTask()
        }
    }

}

@Composable
fun TaskComposable(task: Task) {
    val repository = get<ITodoRepository>()

    var alertDialogVisible by remember { mutableStateOf(false) }
    if (alertDialogVisible) {
        AlertDialog(onDismissRequest = {

        }, title = { Text("Are you sure to delete?") },
            confirmButton = {
                Button(onClick = {
                    alertDialogVisible = false
                    repository.remove(task.id)
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = {
                    alertDialogVisible = false
                }) {
                    Text("Cancel")
                }
            })
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {
            alertDialogVisible = true
        }) {
            Icon(
                Icons.Rounded.Delete,
                "Delete task",
                tint = Color.Red,
            )
        }
        IconButton(onClick = {
            if (task.completed) {
                repository.uncomplete(task.id)
            } else {
                repository.complete(task.id)
            }
        }) {
            Icon(
                if (task.completed) Icons.Default.CheckCircle else Icons.Default.Check,
                "Toggle completion",
                tint = if (task.completed) Color.Black else Color.LightGray
            )
        }
        Text(
            task.name,
            fontSize = 24.sp,
            style = TextStyle(textDecoration = if (task.completed) TextDecoration.LineThrough else TextDecoration.None)
        )
    }
}

@Composable
fun NewTask() {
    val repository = get<ITodoRepository>()

    var name by remember { mutableStateOf("") }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        BasicTextField(
            value = name,
            textStyle = TextStyle(fontSize = 24.sp),
            onValueChange = {
                name = it
            }, modifier = Modifier
                .padding(8.dp)
                .height(40.dp)
                .border(width = 1.dp, color = Color.Black)
                .background(Color.LightGray)
                .fillMaxWidth()
                .padding(8.dp)
        )
        Button(onClick = {
            repository.add(name)
            name = ""
        }) {
            Row {
                Text("Add task", fontSize = 24.sp)
            }
        }
    }
}
