package com.example.todotest.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.todotest.App
import com.example.todotest.data.TodoDao
import com.example.todotest.data.TodoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(private val todoDao: TodoDao) : ViewModel() {

    fun getTodo(id: Int): Flow<TodoEntity?> {
        return todoDao.get(id).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
    }

    fun addTodo(content: String) {
        viewModelScope.launch {
            todoDao.insert(TodoEntity(content = content))
        }
    }

    fun updateTodo(todo: TodoEntity) {
        viewModelScope.launch {
            todoDao.update(todo)
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
                return DetailViewModel(application.db.todoDao()) as T
            }
        }
    }
}
