package com.example.breathingapps2.models

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Interval (
    @field:SerializedName("id")
    @get:PropertyName("id")
    @set:PropertyName("id")
    var id : String ?= null,

    @field:SerializedName("name")
    @get:PropertyName("name")
    @set:PropertyName("name")
    var name : String ?= null,

    @field:SerializedName("inhale")
    @get:PropertyName("inhale")
    @set:PropertyName("inhale")
    var inhale : Int ?= null,

    @field:SerializedName("hold")
    @get:PropertyName("hold")
    @set:PropertyName("hold")
    var hold : Int ?= null,

    @field:SerializedName("exhale")
    @get:PropertyName("exhale")
    @set:PropertyName("exhale")
    var exhale : Int ?= null,

    @field:SerializedName("endHold")
    @get:PropertyName("endHold")
    @set:PropertyName("endHold")
    var endHold : Int ?= null,

    @field:SerializedName("cycles")
    @get:PropertyName("cycles")
    @set:PropertyName("cycles")
    var cycles : Int ?= null,

    @field:SerializedName("keyInterval")
    @get:PropertyName("keyInterval")
    @set:PropertyName("keyInterval")
    var keyInterval : String ?= null
        ) : Parcelable, Serializable