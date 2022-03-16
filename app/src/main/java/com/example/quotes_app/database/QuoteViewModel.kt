package com.example.quotes_app.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class QuoteViewModel(application: Application):AndroidViewModel(application) {

    val readAllData:LiveData<List<Quote>>
    private val respository:QuoteRepository

    init {
        val dao = QuoteDatabase.getDatabase(application).quoteDao()
        respository = QuoteRepository(dao)
        readAllData = respository.readAllData
    }

    fun insert(quote:Quote) {
        viewModelScope.launch(Dispatchers.IO) {
            respository.insert(quote)
        }
    }
}