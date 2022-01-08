package com.example.breathingapps2.models

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(

    @field:SerializedName("title")
    @get:PropertyName("title")
    @set:PropertyName("title")
    var title : String ?= null,

    @field:SerializedName("body")
    @get:PropertyName("body")
    @set:PropertyName("body")
    var body : String ?= null,

    @field:SerializedName("image")
    @get:PropertyName("image")
    @set:PropertyName("image")
    var image : String ?= null
) : Parcelable