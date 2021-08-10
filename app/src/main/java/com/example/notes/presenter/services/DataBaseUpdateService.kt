package com.example.notes.presenter.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*

class DataBaseUpdateService: Service() {
    companion object {
        const val FILTER_KEY = "com.example.notes.classes.services.DataBaseUpdateService.FILTER_KEY"
        const val UPDATE_PERIOD = 300000L // 5m=300000L
    }

    private val timer = Timer()

    override fun onCreate() {
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    sendBroadcast(Intent(FILTER_KEY))
                }
            },
            0,
            UPDATE_PERIOD
        )
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        timer.cancel()
    }
}

