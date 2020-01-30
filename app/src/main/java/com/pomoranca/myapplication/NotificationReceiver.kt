package com.pomoranca.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat


class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationHelper = NotificationHelper(context)
        val nb: NotificationCompat.Builder = notificationHelper.channelNotification
        notificationHelper.manager!!.notify(1, nb.build())
    }
}