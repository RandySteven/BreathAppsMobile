package com.example.breathingapps2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.breathingapps2.MainActivity
import com.example.breathingapps2.R
import com.example.breathingapps2.databinding.CustomRelaxationSettingBinding
import com.example.breathingapps2.repository.Repository
import com.google.firebase.database.FirebaseDatabase

class SettingIntervalFragment : Fragment(), SeekBar.OnSeekBarChangeListener {

    private var inhaleProgress : Int = 0
    private var exhaleProgress : Int = 0
    private var endHoldProgress : Int = 0
    private var cyclesProgress : Int = 0
    private var holdProgress : Int = 0

    private val max = 60
    private val min = 0
    private val step = 1

    private var _binding : CustomRelaxationSettingBinding ?= null
    private val customRelaxationSettingBinding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomRelaxationSettingBinding.inflate(layoutInflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var seekBarInhale = customRelaxationSettingBinding?.seekBarInhale
        var seekBarExhale = customRelaxationSettingBinding?.seekBarExhale
        var seekBarEndHold = customRelaxationSettingBinding?.seekBarEndHold
        var seekBarCycles = customRelaxationSettingBinding?.seekBarCycles
        var seekBarHold = customRelaxationSettingBinding?.seekBarHold

        seekBarInhale!!.max = (max - min) / step
        seekBarExhale!!.max = (max - min) / step
        seekBarEndHold!!.max = (max - min) / step
        seekBarCycles!!.max = (max - min) / step
        seekBarHold!!.max = (max - min) / step

        seekBarInhale!!.setOnSeekBarChangeListener(this)
        seekBarExhale!!.setOnSeekBarChangeListener(this)
        seekBarEndHold!!.setOnSeekBarChangeListener(this)
        seekBarCycles!!.setOnSeekBarChangeListener(this)
        seekBarHold!!.setOnSeekBarChangeListener(this)

        onClick()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when(seekBar?.id){
            R.id.seekBarInhale -> {
                inhaleProgress = progress
                customRelaxationSettingBinding?.tvInhaleValue?.text = "$inhaleProgress seconds"
            }
            R.id.seekBarExhale -> {
                exhaleProgress = progress
                customRelaxationSettingBinding?.tvExhaleValue?.text = "$exhaleProgress seconds"
            }
            R.id.seekBarEndHold -> {
                endHoldProgress = progress
                customRelaxationSettingBinding?.tvEndHoldValue?.text = "$endHoldProgress seconds"
            }
            R.id.seekBarCycles -> {
                cyclesProgress = progress
                customRelaxationSettingBinding?.tvCyclesValue?.text = "$cyclesProgress times"
            }
            R.id.seekBarHold -> {
                holdProgress = progress
                customRelaxationSettingBinding?.tvHoldValue?.text = "$holdProgress seconds"
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        when(seekBar?.id){
            R.id.seekBarInhale -> {
                customRelaxationSettingBinding?.tvInhaleValue?.text = "0 seconds"
            }
            R.id.seekBarExhale -> {
                customRelaxationSettingBinding?.tvExhaleValue?.text = "0 seconds"
            }
            R.id.seekBarEndHold -> {
                customRelaxationSettingBinding?.tvEndHoldValue?.text = "0 seconds"
            }
            R.id.seekBarCycles -> {
                customRelaxationSettingBinding?.tvCyclesValue?.text = "0 times"
            }
            R.id.seekBarHold -> {
                customRelaxationSettingBinding?.tvHoldValue?.text = "0 seconds"
            }
        }
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        when(seekBar?.id){
            R.id.seekBarInhale -> {
                customRelaxationSettingBinding?.tvInhaleValue?.text = "$inhaleProgress seconds"
            }
            R.id.seekBarExhale -> {
                customRelaxationSettingBinding?.tvExhaleValue?.text = "$exhaleProgress seconds"
            }
            R.id.seekBarEndHold -> {
                customRelaxationSettingBinding?.tvEndHoldValue?.text = "$endHoldProgress seconds"
            }
            R.id.seekBarCycles -> {
                customRelaxationSettingBinding?.tvCyclesValue?.text = "$cyclesProgress times"
            }
            R.id.seekBarHold -> {
                customRelaxationSettingBinding?.tvHoldValue?.text = "$holdProgress seconds"
            }
        }
    }

//    name : String, inhale : Int, hold : Int, exhale : Int, endHold : Int, cycles : Int
    private fun onClick(){

        var name = customRelaxationSettingBinding?.etNameInterval?.text
        println(name.toString())

        customRelaxationSettingBinding?.buttonConfirm?.setOnClickListener {
            customRelaxationSettingBinding?.etNameInterval?.text = null
            customRelaxationSettingBinding?.seekBarInhale?.progress = 0
            customRelaxationSettingBinding?.seekBarCycles?.progress = 0
            customRelaxationSettingBinding?.seekBarEndHold?.progress = 0
            customRelaxationSettingBinding?.seekBarHold?.progress = 0
            customRelaxationSettingBinding?.seekBarExhale?.progress = 0
            Repository.addInterval(name.toString(), inhaleProgress, holdProgress, exhaleProgress, endHoldProgress, cyclesProgress)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_main, HomeFragment())
                ?.commit()
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
        }

    }
}