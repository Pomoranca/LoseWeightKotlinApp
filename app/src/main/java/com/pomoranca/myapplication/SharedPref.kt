package com.pomoranca.myapplication

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {

    internal var mySharedPref: SharedPreferences

    init {
        mySharedPref = context.getSharedPreferences("preferences_theme", Context.MODE_PRIVATE)
    }

    fun setNightTheme(state: Boolean?) {
        val editor = mySharedPref.edit()
        editor.putBoolean("night_theme", state!!)
        editor.apply()
    }

    fun loadNightTheme() : Boolean?{
        return mySharedPref.getBoolean("night_theme", false)
    }


}
