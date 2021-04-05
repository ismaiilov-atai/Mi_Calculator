package com.example.calculator.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(HistoryItem::class), version = 1)
abstract class HistoryDatabase : RoomDatabase() {
    companion object {
        var instance: HistoryDatabase? = null
    }

    abstract fun historyDao(): HistoryDao
}