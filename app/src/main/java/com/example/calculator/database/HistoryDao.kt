package com.example.calculator.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {
    @Query("SELECT * FROM historyitem")
    fun getAll(): List<HistoryItem>

    @Insert
    fun insertAll(vararg history: HistoryItem)

    @Delete
    fun delete(vararg history: HistoryItem)

    @Query("DELETE FROM historyitem")
    fun deleteAll()
}