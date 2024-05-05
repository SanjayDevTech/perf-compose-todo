package com.example.todotest.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.todotest.App
import com.example.todotest.data.TodoDao
import com.example.todotest.data.TodoEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val todoDao: TodoDao) : ViewModel() {
    val todosFlow = todoDao
        .getAll()
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )


    fun deleteTodo(todo: TodoEntity) {
        viewModelScope.launch {
            todoDao.delete(todo.id)
        }
    }

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras,
            ): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as App
                return MainViewModel(application.db.todoDao()) as T
            }
        }
    }
}
