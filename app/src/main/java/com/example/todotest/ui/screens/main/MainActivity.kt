package com.example.todotest.ui.screens.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todotest.TodoItem
import com.example.todotest.ui.screens.detail.DetailActivity
import com.example.todotest.ui.theme.TodoTestTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel by viewModels<MainViewModel> { MainViewModel.Factory }
        setContent {
            val todos by viewModel.todosFlow.collectAsStateWithLifecycle()
            val context = LocalContext.current
            TodoTestTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = {
                            Text(text = "Todo Test App")
                        })
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            val intent = Intent(context, DetailActivity::class.java)
                            startActivity(intent)
                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Todo")
                        }
                    }
                ) { paddingValues ->
                    LazyColumn(
                        modifier = Modifier
                            .padding(paddingValues)
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(todos, key = { it.id }) { todo ->
                            TodoItem(todo = todo, onDelete = {
                                viewModel.deleteTodo(todo)
                            }) {
                                val intent = Intent(context, DetailActivity::class.java).apply {
                                    putExtra("id", todo.id)
                                }
                                startActivity(intent)
                            }
                        }
                    }
                }
            }
        }
    }
}
