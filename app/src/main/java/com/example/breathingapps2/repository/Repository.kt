package com.example.breathingapps2.repository

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.example.breathingapps2.models.Interval
import com.example.breathingapps2.models.Song
import com.example.breathingapps2.models.Video
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

object Repository {

    fun addDataSong(){
        val databaseSongs = FirebaseDatabase.getInstance().getReference("songs")
        val songs = mutableListOf<Song?>()

        FirebaseStorage
            .getInstance("gs://breathing-apps.appspot.com/")
            .reference
            .child("musics")
            .listAll()
            .addOnSuccessListener { listResult ->
                listResult.items.forEach { item ->
                    item.downloadUrl
                        .addOnSuccessListener { uri ->
                            val names = item.name.split("_")
                            val artistNames = names[0].trim()
                            val albumName = names[1].trim()
                            val songName = names[2].trim()
                            val yearAlbum = names[3].trim().replace(".mp3", "")

                            val keySong = databaseSongs.push().key
                            songs.add(Song(
                                keySong = keySong,
                                albumNameSong = albumName,
                                nameSong = songName,
                                uriSong = uri.toString(),
                                artistSong = artistNames,
                                yearSong = yearAlbum.toInt()
                            ))
                            databaseSongs.setValue(songs)
                        }
                        .addOnFailureListener { e ->
                            Log.e("Repository", "[addDataToTopChart - download] ${e.printStackTrace()}")
                            Log.e("Repository", "[addDataToTopChart - download] ${e.message}")
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.e("Repository", "[addDataToTopChart] ${e.printStackTrace()}")
                Log.e("Repository", "[addDataToTopChart] ${e.message}")
            }
    }

    fun addDataToSongsImage(){
        val databaseSongs = FirebaseDatabase.getInstance().getReference("songs")
        FirebaseStorage
            .getInstance("gs://breathing-apps.appspot.com/")
            .reference
            .child("images")
            .listAll()
            .addOnSuccessListener { listResult ->
                listResult.items.forEach{ item ->
                    item.downloadUrl
                        .addOnSuccessListener { uri ->
                            val albumName = item.name.replace(".jpg", "").trim()
                            databaseSongs
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        for(snap in snapshot.children){
                                            val song = snap.getValue(Song::class.java)
                                            if(song?.albumNameSong == albumName){
                                                databaseSongs.child(snap.key.toString())
                                                    .updateChildren(mapOf(
                                                        "image_song" to uri.toString()
                                                    ))
                                            }
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        Log.e("Repository", "[onCancelled] ${error.message}")
                                    }
                                })

                        }
                        .addOnFailureListener { e ->
                            Log.e("Repository", "[addDataToTopChartsImage-downloadURL] ${e.message}")
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.e("Repository", "[addDataToTopChartsImage] ${e.message}")
            }
    }

    fun addDataVideo(){
        val databaseVideos = FirebaseDatabase.getInstance().getReference("videos")
        val videos = mutableListOf<Video?>()

        FirebaseStorage
            .getInstance("gs://breathing-apps.appspot.com/")
            .reference
            .child("videos")
            .listAll()
            .addOnSuccessListener { listResult ->
                listResult.items.forEach { item ->
                    item.downloadUrl
                        .addOnSuccessListener { uri ->
                            val names = item.name.split("_")
                            val videoTitle = names[0].trim()
                            val videoCreator = names[1].trim()
                            val videoDesc = names[2].trim()
                            val yearVideo = names[3].trim().replace(".mp4", "")

                            val keyVideo = databaseVideos.push().key
                            videos.add(Video(
                                videoTitle = videoTitle,
                                videoCreator =  videoCreator,
                                videoDesc = videoDesc,
                                uriVideo = uri.toString(),
                                keyVideo = keyVideo.toString(),
                                yearVideo = yearVideo.toInt()
                                ))
                            databaseVideos.setValue(videos)
                        }
                        .addOnFailureListener { e ->
                            Log.e("Repository", "[addDataToTopChart - download] ${e.printStackTrace()}")
                            Log.e("Repository", "[addDataToTopChart - download] ${e.message}")
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.e("Repository", "[addDataToTopChart] ${e.printStackTrace()}")
                Log.e("Repository", "[addDataToTopChart] ${e.message}")
            }
    }

    fun addInterval(name : String, inhale : Int, hold : Int, exhale : Int, endHold : Int, cycles : Int){
        val databaseInterval = FirebaseDatabase.getInstance().getReference("intervals")

        var intervalId = databaseInterval.push().key
        val interval : Interval = Interval(
            id = intervalId,
            name = name,
            inhale = inhale,
            hold = hold,
            exhale = exhale,
            endHold = endHold,
            cycles = cycles,
            keyInterval = databaseInterval.push().key
        )
        databaseInterval.child(intervalId.toString()).setValue(interval).addOnCompleteListener {
            Log.d("Success", "addInterval: success")
        }
    }
}