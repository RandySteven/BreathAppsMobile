package com.example.breathingapps2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.breathingapps2.databinding.CustomRelaxationPage2Binding
import com.example.breathingapps2.models.Interval
import org.jetbrains.anko.startActivity
import java.io.Serializable

class IntervalDetailActivity : AppCompatActivity() {

    companion object{
        const val KEY_INTERVALS = "key_intervals"
        const val KEY_POSITION = "key_position"
    }

    private var position = 0
    private var intervals : MutableList<Interval> ?= null
    private var customRelaxationPage2Binding : CustomRelaxationPage2Binding ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customRelaxationPage2Binding = CustomRelaxationPage2Binding.inflate(layoutInflater)
        setContentView(customRelaxationPage2Binding?.root)

        getData()
    }

    private fun getData(){
        if(intent != null){
            intervals = intent.getParcelableArrayListExtra(KEY_INTERVALS)
            position = intent.getIntExtra(KEY_POSITION, 0)
            intervals?.let{
                val interval = it[position]
                initView(interval)
            }
        }
    }

    private fun initView(interval: Interval){
        customRelaxationPage2Binding?.customRelaxationtitle?.text = interval.name
        customRelaxationPage2Binding?.tvInhale?.text = "${interval.inhale} seconds"
        customRelaxationPage2Binding?.tvHold?.text = "${interval.hold} seconds"
        customRelaxationPage2Binding?.tvExhale?.text = "${interval.exhale} seconds"
        customRelaxationPage2Binding?.tvEndHold?.text = "${interval.endHold} seconds"
        customRelaxationPage2Binding?.tvCycles?.text = "${interval.cycles} times"
        customRelaxationPage2Binding?.backArrow?.setOnClickListener {
            finish()
            startActivity<MainActivity>()
        }
        customRelaxationPage2Binding?.buttonStart?.setOnClickListener {
            intent = Intent(this, DailyExerciseActivity::class.java)
            intent.putExtra("interval", interval as Serializable)
            startActivity(intent)
            finish()
        }
    }

}