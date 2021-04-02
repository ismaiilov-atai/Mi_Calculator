package com.example.calculator.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryItem(
    @ColumnInfo(name = "math") val math: String?,
    @ColumnInfo(name = "result") val result: String?,
    @PrimaryKey(autoGenerate = true) val uid: Int? = null
)