package com.example.quotes_app.database

import androidx.lifecycle.LiveData


class QuoteRepository(private val quoteDao:QuoteDao) {

    val readAllData:LiveData<List<Quote>> = quoteDao.readAllData()


    suspend fun insert(quote:Quote) {
        quoteDao.insert(quote)
    }

}