package com.android.todo.ui.screens.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.todo.R
import com.android.todo.TodoItem
import com.android.todo.ui.screens.detail.DetailActivity
import com.android.todo.ui.theme.TodoTestTheme

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
                            Text(text = stringResource(id = R.string.app_name))
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
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (todos.isEmpty()) {
                            Text(text = "No records available")
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(todos, key = { it.id }) { todo ->
                                    TodoItem(todo = todo, onDelete = {
                                        viewModel.deleteTodo(todo)
                                    }) {
                                        val intent =
                                            Intent(context, DetailActivity::class.java).apply {
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
    }
}
