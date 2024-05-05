package com.example.todotest.ui.screens.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todotest.ui.theme.TodoTestTheme


class DetailActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getIntExtra("id", 0)

        val isEditMode = id > 0

        val viewModel by viewModels<DetailViewModel> { DetailViewModel.Factory }
        enableEdgeToEdge()
        setContent {
            val todo by remember { viewModel.getTodo(id) }.collectAsStateWithLifecycle(
                initialValue = null,
            )
            var content by remember(todo) {
                mutableStateOf(todo?.content ?: "")
            }
            TodoTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(title = { Text(text = "Detail") })
                }, floatingActionButton = {
                    FloatingActionButton(onClick = {
                        val lTodo = todo
                        if (content.isBlank()) return@FloatingActionButton
                        if (isEditMode) {
                            if (lTodo == null) return@FloatingActionButton
                            viewModel.updateTodo(lTodo.copy(content = content))
                        } else {
                            viewModel.addTodo(content)
                        }
                        finish()
                    }) {
                        Icon(imageVector = Icons.Rounded.Done, contentDescription = "Save Todo")
                    }
                }) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(horizontal = 8.dp)
                            .padding(WindowInsets.ime.asPaddingValues())
                            .fillMaxSize()
                    ) {
                        OutlinedTextField(
                            enabled = todo != null || !isEditMode,
                            value = content,
                            onValueChange = { content = it },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
