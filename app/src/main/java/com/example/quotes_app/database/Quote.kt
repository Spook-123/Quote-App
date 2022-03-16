package com.example.quotes_app.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "quotes_table")
data class Quote(
    val newContent:String,
    val newAuthor:String
) {
    @PrimaryKey(autoGenerate = true) var id = 0
}