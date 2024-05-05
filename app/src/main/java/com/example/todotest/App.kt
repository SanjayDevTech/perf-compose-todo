package com.example.todotest

import android.app.Application
import androidx.room.Room
import com.example.todotest.data.AppDatabase

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
