package com.pomoranca.myapplication.activities


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.activities.fragments.LoginFragmentOne
import com.pomoranca.myapplication.activities.fragments.LoginFragmentTwo


class LoginActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager
    private val PREFS_NAME = "MyPrefsFile"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
        fragmentTransaction.replace(R.id.login_fragment_container, LoginFragmentOne())
        fragmentTransaction.commit()

        val settings: SharedPreferences = getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode
        val hasLoggedIn = settings.getBoolean("hasLoggedIn", false)


        if (hasLoggedIn) {
            //Go directly to main activity.
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
    }





}
