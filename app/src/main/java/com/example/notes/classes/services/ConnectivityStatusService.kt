package com.example.notes.classes.services

import android.app.Service
import android.content.Intent
import android.net.ConnectivityManager
import android.os.IBinder
import java.util.*

class ConnectivityStatusService: Service() {
    companion object {
        const val FILTER_KEY = "com.example.notes.classes.services.ConnectivityStatusService.FILTER_KEY"
        const val STATUS_KEY = "com.example.notes.classes.services.ConnectivityStatusService.FILTER_KEY"
        const val UPDATE_PERIOD = 5000L // 5m=300000L
    }

    private val timer = Timer()

    override fun onCreate() {
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    sendBroadcast(Intent(FILTER_KEY).apply {
                        val cm = applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
                        val activeNetwork = cm.activeNetworkInfo
                        putExtra(STATUS_KEY, activeNetwork != null && activeNetwork.isConnectedOrConnecting)
                    })
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