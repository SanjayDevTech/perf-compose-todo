package com.android.todo.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.android.todo.App
import com.android.todo.data.TodoDao
import com.android.todo.data.TodoEntity
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
