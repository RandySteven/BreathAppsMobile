package com.example.breathingapps2.models

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video (
    @field:SerializedName("video_title")
    @get:PropertyName("video_title")
    @set:PropertyName("video_title")
    var videoTitle : String ?= null,

    @field:SerializedName("video_creator")
    @get:PropertyName("video_creator")
    @set:PropertyName("video_creator")
    var videoCreator : String ?= null,

    @field:SerializedName("video_desc")
    @get:PropertyName("video_desc")
    @set:PropertyName("video_desc")
    var videoDesc : String ?= null,

    @field:SerializedName("uri_video")
    @get:PropertyName("uri_video")
    @set:PropertyName("uri_video")
    var uriVideo : String ?= null,

    @field:SerializedName("key_video")
    @get:PropertyName("key_video")
    @set:PropertyName("key_video")
    var keyVideo : String ?= null,

    @field:SerializedName("year_video")
    @get:PropertyName("year_video")
    @set:PropertyName("year_video")
    var yearVideo : Int ?= null,

    @field:SerializedName("thumbnail_video")
    @get:PropertyName("thumbnail_video")
    @set:PropertyName("thumbnail_video")
    var thumbnailVideo : String ?= null
        ): Parcelable