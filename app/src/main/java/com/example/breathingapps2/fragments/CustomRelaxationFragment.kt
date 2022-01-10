package com.example.breathingapps2.fragments

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.example.breathingapps2.IntervalDetailActivity
import com.example.breathingapps2.R
import com.example.breathingapps2.adapter.IntervalsAdapter
import com.example.breathingapps2.databinding.FragmentCustomRelaxationBinding
import com.example.breathingapps2.models.Interval
import com.example.musicapps.utils.hide
import com.example.musicapps.utils.visible
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.startActivity
import java.lang.reflect.Type

class CustomRelaxationFragment : Fragment() {

    private var _binding: FragmentCustomRelaxationBinding ?= null
    private val customRelaxationBinding get() = _binding
    private lateinit var intervalsAdapter : IntervalsAdapter
    private lateinit var databaseIntervals : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomRelaxationBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intervalsAdapter = IntervalsAdapter()
        databaseIntervals = FirebaseDatabase.getInstance().getReference("intervals")

        swipeInterval()
        onClick()

        showLoading()
        showIntervals()
        Handler(Looper.getMainLooper()).postDelayed({},1000)
    }

    private fun swipeInterval(){
        customRelaxationBinding?.swpCustomRelaxationFragment?.setOnRefreshListener {
            showIntervals()
        }
    }

    private fun onClick(){
        customRelaxationBinding?.btnAddInterval?.setOnClickListener {
            changeFragment(SettingIntervalFragment(), "settingInterval")
            notification()
        }

        intervalsAdapter.onClick { intervals, postion ->
            context?.startActivity<IntervalDetailActivity>(
                IntervalDetailActivity.KEY_INTERVALS to intervals,
                IntervalDetailActivity.KEY_POSITION to postion
            )
        }

        customRelaxationBinding?.backArrow?.setOnClickListener {
            changeFragment(HomeFragment(), "home")
        }
    }

    private fun notification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var channel : NotificationChannel = NotificationChannel("Add Data", "Add new intervals", NotificationManager.IMPORTANCE_DEFAULT)
            var manager : NotificationManager? = context?.getSystemService(NotificationManager::class.java)

            manager?.createNotificationChannel(channel)
        }

        var builder : NotificationCompat.Builder = NotificationCompat.Builder(context?.applicationContext!!, "n")
            .setContentText("Add New Data")
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setAutoCancel(false)
            .setContentText("New data added")

        var managerCompat : NotificationManagerCompat = NotificationManagerCompat.from(context?.applicationContext!!)
        managerCompat.notify(999, builder.build())
    }

    private fun changeFragment(fragment : Fragment, tag : String){
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frame_main, fragment, tag)
            ?.commit()
    }

    private fun hideLoading(){
        customRelaxationBinding?.swpCustomRelaxationFragment?.hide()
    }

    private fun showLoading(){
        customRelaxationBinding?.swpCustomRelaxationFragment?.visible()
    }

    private fun showIntervals(){
        var intervals : MutableList<Interval> = mutableListOf<Interval>()
        databaseIntervals.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                hideLoading()
                if(snapshot!!.exists()){
                    for(snap in snapshot.children){
                        val interval = snap.getValue(Interval::class.java)
                        intervals.add(interval!!)
                    }
                    intervalsAdapter.setData(intervals)
                }
            }
        })

        customRelaxationBinding?.rvIntervals?.adapter = intervalsAdapter
    }
}