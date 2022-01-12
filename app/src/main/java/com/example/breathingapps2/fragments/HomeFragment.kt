package com.example.breathingapps2.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.breathingapps2.DailyExerciseActivity
import com.example.breathingapps2.R
import com.example.breathingapps2.databinding.FragmentHomeBinding
import com.example.breathingapps2.models.Interval
import com.google.firebase.database.*
import org.jetbrains.anko.find
import java.io.Serializable

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding ?= null
    private val homeBinding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        onClick()
    }

    private lateinit var databaseIntervals : DatabaseReference
    private var intervals : MutableList<Interval> = mutableListOf<Interval>()

    private fun init(){
        databaseIntervals = FirebaseDatabase.getInstance().getReference("intervals")

        databaseIntervals.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot!!.exists()){
                    for(snap in snapshot.children){
                        val interval = snap.getValue(Interval::class.java)
                        intervals.add(interval!!)
                    }
                }
            }
        })
    }

    private fun onClick(){
        homeBinding?.buttonCustom?.setOnClickListener {
            changeFragment(CustomRelaxationFragment())
        }

        homeBinding?.buttonDaily?.setOnClickListener {
            activity?.let{
                val intent = Intent(it, DailyExerciseActivity::class.java)
                intent.putExtra("interval", intervals?.get(intervals.size.minus(1)) as Serializable)
                it.startActivity(intent)
            }
        }

        homeBinding?.buttonMusic?.setOnClickListener {
            changeFragment(MusicFragment())
        }

        homeBinding?.buttonVideo?.setOnClickListener {
            changeFragment(VideoFragment())
        }
    }

    private fun changeFragment(fragment : Fragment){
        var fr = fragmentManager?.beginTransaction()
        fr?.replace(R.id.frame_main, fragment)
        fr?.commit()
    }

}