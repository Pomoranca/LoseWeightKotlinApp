package com.pomoranca.myapplication

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
    private val settingsPref: SharedPreferences =
        context.getSharedPreferences("theme", Context.MODE_PRIVATE)

    fun saveNightModeState(state: Boolean) {
        val editor: SharedPreferences.Editor = settingsPref.edit()
        editor.putBoolean("NightMode", state)
        editor.apply()
    }

    fun loadNightModeState(): Boolean {
        return settingsPref.getBoolean("NightMode", false)
    }

    fun saveNotificationState(state: Boolean) {
        val editor: SharedPreferences.Editor = settingsPref.edit()
        editor.putBoolean("NotificationState", state)
        editor.apply()
    }

    fun saveNotificationTime(time: String) {
        val editor: SharedPreferences.Editor = settingsPref.edit()
        editor.putString("NotificationTime", time)
        editor.apply()

    }

    fun loadNotificationState(): Boolean {
        return settingsPref.getBoolean("NotificationState", false)

    }

    fun loadNotificationTime(): String? {
        return settingsPref.getString("NotificationTime", "")
    }


}