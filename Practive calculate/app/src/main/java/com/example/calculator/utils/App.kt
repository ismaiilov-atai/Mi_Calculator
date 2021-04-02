package com.example.calculator.utils

import android.app.Application
import androidx.room.Room
import com.example.calculator.database.HistoryDatabase

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        HistoryDatabase.instance = Room.databaseBuilder(applicationContext, HistoryDatabase::class.java, "history").build()
    }
}