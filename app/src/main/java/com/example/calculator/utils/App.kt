package com.example.calculator.utils

import android.app.Application
import androidx.room.Room
import com.example.calculator.database.HistoryDatabase
import com.example.calculator.utils.prefs.Prefs

class App: Application() {

    companion object {
        var prefs: Prefs? = null
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        HistoryDatabase.instance = Room.databaseBuilder(applicationContext, HistoryDatabase::class.java, "history").build()
        instance = this
        prefs = Prefs(applicationContext)
    }
}