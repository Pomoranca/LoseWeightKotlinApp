package com.pomoranca.myapplication

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
    private val settingsPref: SharedPreferences = context.getSharedPreferences("SETTINGS", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = settingsPref.edit()


    fun saveUserName(name: String) {
        editor.putString("Username", name)
        editor.apply()
    }

    fun loadUserName(): String? {
        return settingsPref.getString("Username", "")
    }


    fun saveNightModeState(state: Boolean) {
        editor.putBoolean("NightMode", state)
        editor.apply()
    }

    fun loadNightModeState(): Boolean {
        return settingsPref.getBoolean("NightMode", false)
    }


    fun saveNarrationState(state: Boolean) {
        editor.putBoolean("NarrationState", state)
        editor.apply()
    }

    fun loadNarrationState(): Boolean {
        return settingsPref.getBoolean("NarrationState", true)
    }

    fun saveNotificationState(state: Boolean) {
        editor.putBoolean("NotificationState", state)
        editor.apply()
    }

    fun saveNotificationTime(time: String) {
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