package com.example.breathingapps2

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.example.breathingapps2.databinding.ActivityDailyExerciseBinding
import com.example.breathingapps2.models.Interval
import com.example.musicapps.utils.gone
import org.jetbrains.anko.startActivity
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class DailyExerciseActivity : AppCompatActivity() {

    private var dailyExerciseBinding : ActivityDailyExerciseBinding ?= null
    private var interval : Interval? = null
    private var isCancelled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        interval = intent?.getSerializableExtra("interval") as Interval
        dailyExerciseBinding = ActivityDailyExerciseBinding.inflate(layoutInflater)
        setContentView(dailyExerciseBinding?.root)
        println(interval)

        var millisInhale : Long = interval?.inhale?.times(1000)?.toLong()!!

        countdownInhale(millisInhale)

        dailyExerciseBinding?.backArrow?.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
    }

    /**
     * Countdown
     */
    private fun countdownInhale(millisInhale : Long){
        dailyExerciseBinding?.tvMethod?.text = "Inhale"
        dailyExerciseBinding?.btnStart?.setOnClickListener{
            // Start the timer
            timerInhale(millisInhale,1000).start()
            it.isEnabled = false
            dailyExerciseBinding?.btnStop?.isEnabled = true

            isCancelled = false

        }

        dailyExerciseBinding?.btnStop?.setOnClickListener {
            isCancelled = true

            it.isEnabled = false
            dailyExerciseBinding?.btnStart?.isEnabled = true
        }
    }

    private fun countdownHold(millisHold : Long){
        dailyExerciseBinding?.tvMethod?.text = "Hold"
        dailyExerciseBinding?.btnStart?.setOnClickListener{
            // Start the timer
            timerHold(millisHold,1000).start()
            it.isEnabled = false
            dailyExerciseBinding?.btnStop?.isEnabled = true

            isCancelled = false
        }

        dailyExerciseBinding?.btnStop?.setOnClickListener {
            isCancelled = true

            it.isEnabled = false
            dailyExerciseBinding?.btnStart?.isEnabled = true
        }
    }

    private fun countdownExhale(millisExhale : Long){
        dailyExerciseBinding?.tvMethod?.text = "Exhale"
        dailyExerciseBinding?.btnStart?.setOnClickListener{
            // Start the timer
            timerExhale(millisExhale,1000).start()
            it.isEnabled = false
            dailyExerciseBinding?.btnStop?.isEnabled = true

            isCancelled = false
        }

        dailyExerciseBinding?.btnStop?.setOnClickListener {
            isCancelled = true

            it.isEnabled = false
            dailyExerciseBinding?.btnStart?.isEnabled = true
        }
    }

    private fun countDownEndHold(millisEndHold : Long){
        dailyExerciseBinding?.tvMethod?.text = "End Hold"
        dailyExerciseBinding?.btnStart?.setOnClickListener{
            // Start the timer
            timerEndHold(millisEndHold,1000).start()
            it.isEnabled = false
            dailyExerciseBinding?.btnStop?.isEnabled = true

            isCancelled = false
        }

        dailyExerciseBinding?.btnStop?.setOnClickListener {
            isCancelled = true

            it.isEnabled = false
            dailyExerciseBinding?.btnStart?.isEnabled = true
        }
    }

    private fun countDownCycles(cycles : Long){
        dailyExerciseBinding?.tvMethod?.text = "Cycles"
        dailyExerciseBinding?.tvSeconds?.text = "${cycles} Times"
        dailyExerciseBinding?.btnStart?.isVisible = false
        dailyExerciseBinding?.btnStop?.isVisible = false
        dailyExerciseBinding?.btnFinish?.isVisible = true
        dailyExerciseBinding?.btnFinish?.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
    }

    /**
     * ==================================================================================================================================
     * Timer
     * ==================================================================================================================================
     */
    private fun timerInhale(millisInput : Long, countDownInterval : Long) : CountDownTimer{
        return object : CountDownTimer(millisInput, countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                val timeRemaining = timeString(millisUntilFinished)
                if(isCancelled){
                    dailyExerciseBinding?.tvSeconds?.text =  "${dailyExerciseBinding?.tvSeconds?.text}\nStopped.(Cancelled)"
                    cancel()
                }else{
                    dailyExerciseBinding?.tvSeconds?.text = timeRemaining
                }
            }

            override fun onFinish() {
                dailyExerciseBinding?.tvSeconds?.text = "Finish"

                dailyExerciseBinding?.btnStart?.isEnabled = true
                dailyExerciseBinding?.btnStop?.isEnabled = false

                var millisHold : Long = interval?.hold?.times(1000)?.toLong()!!
                countdownHold(millisHold)
            }
        }
    }

    private fun timerHold(millisInput : Long, countDownInterval : Long) : CountDownTimer{
        return object : CountDownTimer(millisInput, countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                val timeRemaining = timeString(millisUntilFinished)
                if(isCancelled){
                    dailyExerciseBinding?.tvSeconds?.text =  "${dailyExerciseBinding?.tvSeconds?.text}\nStopped.(Cancelled)"
                    cancel()
                }else{
                    dailyExerciseBinding?.tvSeconds?.text = timeRemaining
                }
            }

            override fun onFinish() {
                dailyExerciseBinding?.tvSeconds?.text = "Finish"

                dailyExerciseBinding?.btnStart?.isEnabled = true
                dailyExerciseBinding?.btnStop?.isEnabled = false

                var millisExhale : Long = interval?.exhale?.times(1000)?.toLong()!!
                countdownExhale(millisExhale)
            }
        }
    }

    private fun timerExhale(millisInput : Long, countDownInterval : Long) : CountDownTimer{
        return object : CountDownTimer(millisInput, countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                val timeRemaining = timeString(millisUntilFinished)
                if(isCancelled){
                    dailyExerciseBinding?.tvSeconds?.text =  "${dailyExerciseBinding?.tvSeconds?.text}\nStopped.(Cancelled)"
                    cancel()
                }else{
                    dailyExerciseBinding?.tvSeconds?.text = timeRemaining
                }
            }

            override fun onFinish() {
                dailyExerciseBinding?.tvSeconds?.text = "Finish"

                dailyExerciseBinding?.btnStart?.isEnabled = true
                dailyExerciseBinding?.btnStop?.isEnabled = false

                var millisEndHold : Long = interval?.endHold?.times(1000)?.toLong()!!
                countDownEndHold(millisEndHold)
            }
        }
    }

    private fun timerEndHold(millisInput : Long, countDownInterval : Long) : CountDownTimer{
        return object : CountDownTimer(millisInput, countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                val timeRemaining = timeString(millisUntilFinished)
                if(isCancelled){
                    dailyExerciseBinding?.tvSeconds?.text =  "${dailyExerciseBinding?.tvSeconds?.text}\nStopped.(Cancelled)"
                    cancel()
                }else{
                    dailyExerciseBinding?.tvSeconds?.text = timeRemaining
                }
            }

            override fun onFinish() {
                dailyExerciseBinding?.tvSeconds?.text = "Finish"

                dailyExerciseBinding?.btnStart?.isEnabled = true
                dailyExerciseBinding?.btnStop?.isEnabled = false

                var cycles : Long = interval?.cycles?.toLong()!!
                countDownCycles(cycles)
            }
        }
    }


    /**
     * ========================================================================================================================================
     */

    private fun timeString(millisUntilFinished : Long) : String{
        var millisUntilFinished:Long = millisUntilFinished
        val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
        millisUntilFinished -= TimeUnit.DAYS.toMillis(days)

        val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
        millisUntilFinished -= TimeUnit.HOURS.toMillis(hours)

        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
        millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes)

        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)

        // Format the string
        return String.format(
            Locale.getDefault(),
            "%02d day: %02d hour: %02d min: %02d sec",
            days,hours, minutes,seconds
        )
    }

//    private fun startTimeCounter(){
//        var timer : Int = interval?.hold?.times(1000)!!
//        object : CountDownTimer(timer.toLong(), 1000){
//            override fun onTick(millisUntilFinished: Long) {
//                dailyExerciseBinding?.btnCountDown?.text = interval?.hold?.toString(
//                    interval?.hold?.minus(1)!!
//                )
//            }
//
//            override fun onFinish() {
//                dailyExerciseBinding?.btnCountDown?.text = "0"
//            }
//        }.start()
//    }
}