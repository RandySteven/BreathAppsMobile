package com.example.breathingapps2.models

data class Qod(
    val contents : Contents
)

data class Contents(
    val quotes : ArrayList<Quote>
    )

data class Quote(
    val quote : String,
    val author : String,
    val date : String,
    val background : String
)

