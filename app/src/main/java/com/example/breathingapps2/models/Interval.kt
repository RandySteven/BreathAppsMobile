package com.example.breathingapps2.models

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Interval (
    @field:SerializedName("id")
    @get:PropertyName("id")
    @set:PropertyName("id")
    var id : Int ?= null,

    @field:SerializedName("inhale")
    @get:PropertyName("inhale")
    @set:PropertyName("inhale")
    var inhale : Int,

    @field:SerializedName("hold")
    @get:PropertyName("hold")
    @set:PropertyName("hold")
    var hold : Int,

    @field:SerializedName("exhale")
    @get:PropertyName("exhale")
    @set:PropertyName("exhale")
    var exhale : Int,

    @field:SerializedName("endHold")
    @get:PropertyName("endHold")
    @set:PropertyName("endHold")
    var endHold : Int,

    @field:SerializedName("cycles")
    @get:PropertyName("cycles")
    @set:PropertyName("cycles")
    var cycles : Int
        ) : Parcelable