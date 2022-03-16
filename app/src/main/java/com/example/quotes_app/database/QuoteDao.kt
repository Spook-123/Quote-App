package com.example.quotes_app.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(quote:Quote)

    @Query("SELECT * FROM quotes_table ORDER BY id ASC")
    fun readAllData():LiveData<List<Quote>>
}