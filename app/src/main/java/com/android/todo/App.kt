package com.android.todo

import android.app.Application
import androidx.room.Room
import com.android.todo.data.AppDatabase

class App : Application() {
    val db by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "app-database"
        )
            .build()
    }
}
