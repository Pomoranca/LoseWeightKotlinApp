package com.pomoranca.myapplication.activities


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.activities.fragments.LoginFragmentOne


class LoginActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager
    private val PREFS_NAME = "MyPrefsFile"

    private lateinit var fragmentOne: Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //get fragment instance so we can restore state

        //begin fragment transaction and animate
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_right,
            R.anim.enter_from_right,
            R.anim.exit_to_right
        )
        fragmentTransaction.replace(R.id.login_fragment_container, LoginFragmentOne())
        fragmentTransaction.commit()


        val settings: SharedPreferences =
            getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode
        val hasLoggedIn = settings.getBoolean("hasLoggedIn", false)


        if (hasLoggedIn) {
            //Go directly to main activity.
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
    }



}
