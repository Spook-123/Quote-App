package com.example.quotes_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.quotes_app.data.QuoteData
import com.example.quotes_app.data.QuoteService
import com.example.quotes_app.database.Quote
import com.example.quotes_app.database.QuoteViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.lastIndexOf as lastIndexOf1

private const val BASE_URL = "https://api.quotable.io/"
class MainActivity : AppCompatActivity() {

    private lateinit var quoteViewModel:QuoteViewModel
    val allQuotes = ArrayList<Quote>()
    //private lateinit var quote:Quote
    var count = 0
    var shareText:String? = null
    var shareAuthor:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Toast.makeText(this,"$size of Array",Toast.LENGTH_LONG).show()

        if(allQuotes.isEmpty()) {
            getQuotes()
        }



        quoteViewModel = ViewModelProvider(this).get(QuoteViewModel::class.java)

        btnNext.setOnClickListener {
            proBar.visibility = View.VISIBLE
            //Toast.makeText(this,"Count-> $count",Toast.LENGTH_LONG).show()
            val index = allQuotes.lastIndex
            if(count > 0) {
                count -=1
                //Toast.makeText(this,"if count -> $count",Toast.LENGTH_LONG).show()
                if(allQuotes.isNotEmpty()) {
                    shareText = allQuotes[index-count].newContent
                }
                if(allQuotes.isNotEmpty()) {
                    shareAuthor = allQuotes[index-count].newAuthor
                }
                tvText.text = allQuotes[index-count].newContent
                tvAuthor.text = "Author:- ${allQuotes[index-count].newAuthor}"
                proBar.visibility = View.GONE
            }
            else {
                getQuotes()

            }

        }



        quoteViewModel.readAllData.observe(this, Observer {
            updateData(it)
            //previousQuote(it)
        })

        btnPrev.setOnClickListener {
            previousQuote()
        }

        fabShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,"$shareText\n\n Author:- $shareAuthor")
            //intent.putExtra(Intent.EXTRA_TEXT,shareAuthor)
            val chooser = Intent.createChooser(intent,"Share this using..")
            startActivity(chooser)
        }


    }


    private fun previousQuote() {
        //var prevIndex = allQuotes.size
        //val size = allQuotes.size
        val index = allQuotes.lastIndex-1
        if(index-count < 0) {
            Toast.makeText(this,"No Quotes",Toast.LENGTH_LONG).show()
            return
        }
        if(allQuotes.isNotEmpty()) {
            shareText = allQuotes[index-count].newContent
        }
        if(allQuotes.isNotEmpty()) {
            shareAuthor = allQuotes[index-count].newAuthor
        }
        tvText.text = allQuotes[index-count].newContent
        tvAuthor.text = "Author:- ${allQuotes[index-count].newAuthor}"
        count +=1
        //Toast.makeText(this,"Count -> $count",Toast.LENGTH_LONG).show()


    }

    private fun updateData(quote:List<Quote>) {
        allQuotes.clear()
        allQuotes.addAll(quote)
        if(allQuotes.isNotEmpty()) {
            val curr = allQuotes.lastIndex
            shareText = allQuotes[curr].newContent
            shareAuthor = allQuotes[curr].newAuthor

            //Toast.makeText(this,"Text --> $shareText",Toast.LENGTH_LONG).show()

        }
        //val  size = allQuotes.size
        /**val lastIndex = allQuotes.lastIndex
        //Toast.makeText(this,"size = $size",Toast.LENGTH_LONG).show()
        Toast.makeText(this,"lastIndex = $lastIndex",Toast.LENGTH_LONG).show()**/
    }

    private fun getQuotes() {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(QuoteService::class.java)

        val retroData = retrofit.getData()

        retroData.enqueue(object:Callback<QuoteData>{
            override fun onResponse(call: Call<QuoteData>, response: Response<QuoteData>) {
                val body = response.body()!!
                val setText = body.content
                val setAuthor = body.author
                tvText.text = body.content
                tvAuthor.text = "Author:- ${body.author}"
                val quote = Quote(setText,setAuthor)
                quoteViewModel.insert(quote)
                proBar.visibility = View.GONE

            }

            override fun onFailure(call: Call<QuoteData>, t: Throwable) {

            }

        })
    }


}