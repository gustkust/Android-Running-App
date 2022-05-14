package com.example.sport_road_app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlin.math.roundToInt


class StopWatchFragment : Fragment() {
    var time = 0.0
    var timerStarted = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_stop_watch, container, false)

        val startButton = view.findViewById<Button>(R.id.startButton)
        val resetButton = view.findViewById<Button>(R.id.resetButton)
        val stopButton = view.findViewById<Button>(R.id.stopButton)
        val saveButton = view.findViewById<Button>(R.id.saveButton)

        val stopWatchValueTextView = view.findViewById<TextView>(R.id.stopWatchValueTextView)

        val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                time = intent.getDoubleExtra(StopWatchService.TIME_EXTRA, 0.0)
                stopWatchValueTextView.text = getTimeStringFromDouble(time)
            }
        }


        activity?.registerReceiver(updateTime, IntentFilter(StopWatchService.TIMER_UPDATED))


        startButton.setOnClickListener {
            if (!timerStarted) startTimer()
        }

        resetButton.setOnClickListener {
            resetTimer(stopWatchValueTextView)
        }

        stopButton.setOnClickListener {
            stopTimer()
        }

        saveButton.setOnClickListener {
            saveTime(stopWatchValueTextView)
        }

        return view
    }

    lateinit var dataPasser: OnDataPass

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as OnDataPass
    }

    fun passData(data: String){
        dataPasser.onDataPass(data)
    }

    interface OnDataPass {
        fun onDataPass(data: String)
    }

    private fun saveTime(stopWatchValueTextView: TextView?) {
        if (stopWatchValueTextView != null) {
            passData(stopWatchValueTextView.text.toString())
        }
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hours: Int, minutes: Int, seconds: Int): String {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    private fun resetTimer(stopWatchValueTextView: TextView) {
        stopTimer()
        time = 0.0
        stopWatchValueTextView.text = makeTimeString(0, 0, 0)
    }


    private fun startTimer() {
        val serviceIntent = Intent(activity?.applicationContext, StopWatchService::class.java)
        serviceIntent.putExtra(StopWatchService.TIME_EXTRA, time)
        activity?.startService(serviceIntent)
        timerStarted = true
    }

    private fun stopTimer() {
        val serviceIntent = Intent(activity?.applicationContext, StopWatchService::class.java)
        activity?.stopService(serviceIntent)
        timerStarted = false
    }

}