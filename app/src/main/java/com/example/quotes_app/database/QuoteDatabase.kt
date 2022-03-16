package com.example.quotes_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Quote::class], version = 1, exportSchema = false)
abstract class QuoteDatabase: RoomDatabase() {

    abstract fun quoteDao():QuoteDao

    companion object {

        @Volatile
        private var INSTANCE:QuoteDatabase? = null

        fun getDatabase(context: Context):QuoteDatabase {

            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,QuoteDatabase::class.java,"quotes_database").build()
                INSTANCE = instance
                return instance
            }
        }
    }




}