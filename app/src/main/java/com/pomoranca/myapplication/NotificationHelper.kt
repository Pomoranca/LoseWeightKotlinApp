package com.pomoranca.myapplication

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.pomoranca.myapplication.activities.MainActivity
import com.pomoranca.myapplication.data.Reminders


class NotificationHelper(base: Context?) : ContextWrapper(base) {
    private val reminderList = Reminders()
    private var mManager: NotificationManager? = null
    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(
            channelID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        manager!!.createNotificationChannel(channel)
    }

    val manager: NotificationManager?
        get() {
            if (mManager == null) {
                mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            return mManager
        }

    private val activityIntent = Intent(this, MainActivity::class.java)
    private val contentIntent: PendingIntent = PendingIntent.getActivity(this, 1, activityIntent, 0)!!


    val channelNotification: NotificationCompat.Builder
        get() = NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(reminderList.reminders.random())
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setSmallIcon(R.drawable.ic_check)

    companion object {
        const val channelID = "channelID"
        const val channelName = "Channel Name"
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
    }
}