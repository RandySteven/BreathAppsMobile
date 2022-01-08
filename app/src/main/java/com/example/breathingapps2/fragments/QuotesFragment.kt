package com.example.breathingapps2.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.breathingapps2.R
import com.example.breathingapps2.api.QodApiClient
import com.example.breathingapps2.databinding.FragmentQuotesBinding
import com.example.breathingapps2.models.Qod
import com.example.breathingapps2.models.Quote
import com.example.musicapps.utils.gone
import com.example.musicapps.utils.hide
import com.example.musicapps.utils.visible
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class QuotesFragment : Fragment() {
    private var _binding : FragmentQuotesBinding? = null
    private val quotesBinding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuotesBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeQuotes()

        showLoading()
        loadData()
        Handler(Looper.getMainLooper()).postDelayed({
           hideLoading()
        }, 10000)
    }

    private fun swipeQuotes(){
        quotesBinding?.swipeQuotes?.setOnRefreshListener {
            loadData()
        }
    }

    private fun loadData(){
        QodApiClient.apiService.getQod().enqueue(object : Callback<Qod>{
            override fun onResponse(call: Call<Qod>, response: Response<Qod>) {
                if(response.isSuccessful){
                    println("success call response")
                    val qod : Qod ?= response.body()
                    println(qod)
                    setResponse(qod)
                }
            }

            override fun onFailure(call: Call<Qod>, t: Throwable) {
                t.printStackTrace()
                println("Gagal memanggil API")
            }
        })
    }
    var image : Bitmap ?= null
    private fun setResponse(qod : Qod?){
        quotesBinding?.quoteText?.text = qod?.contents?.quotes?.get(qod?.contents.quotes.size-1)?.quote
        quotesBinding?.textAuthor?.text = qod?.contents?.quotes?.get(qod?.contents.quotes.size-1)?.author
        quotesBinding?.textDate?.text = qod?.contents?.quotes?.get(qod?.contents.quotes.size-1)?.date

        Glide.with(this)
            .load(
                qod?.contents?.quotes?.get(qod?.contents.quotes.size-1)?.background
            )
            .placeholder(
                android.R.color.darker_gray
            )
            .into(quotesBinding?.quoteImage!!)

    }

    private fun hideLoading(){
        quotesBinding?.swipeQuotes?.hide()
    }

    private fun showLoading(){
        quotesBinding?.swipeQuotes?.visible()
    }
}