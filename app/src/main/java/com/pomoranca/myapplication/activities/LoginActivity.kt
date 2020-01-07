package com.pomoranca.myapplication.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.LOG
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.data.User
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel
    private val PREFS_NAME = "MyPrefsFile"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val settings: SharedPreferences = getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode

        val editor = settings.edit()

        //User has successfully logged in, save this information
        // We need an Editor object to make preference changes.

        //Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        val hasLoggedIn = settings.getBoolean("hasLoggedIn", false)

        if (hasLoggedIn) {
            //Go directly to main activity.
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)

        welcomeConfirmButton.setOnClickListener {
            if (TextUtils.isEmpty(welcomeCardInput.text) || TextUtils.isDigitsOnly(welcomeCardInput.text)) {
                welcomeCardInput.error = "You don't have a name ? Come on"
            } else {
                val intent = Intent(this, MainActivity::class.java)
                val name = welcomeCardInput.text.toString().capitalize()
                val user = User(name, 0, 0)
                loseWeightViewModel.insert(user)

                editor.putBoolean("hasLoggedIn", true)
                // Commit the edits!
                editor.apply()
                startActivity(intent)
            }
        }
    }

    fun malePickClicked(view: View) {
        val settings: SharedPreferences = getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode

        val editor = settings.edit()
        editor.putBoolean("pick_male", true)
        editor.apply()

        view.scaleX = 1.2f
        view.scaleY = 1.2f


        pick_female.scaleX = 0.9f
        pick_female.scaleY = 0.9f
    }
    fun femalePickClicked(view: View) {
        val settings: SharedPreferences = getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode

        val editor = settings.edit()
        editor.putBoolean("pick_male", false)
        editor.apply()

        view.scaleX = 1.2f
        view.scaleY = 1.2f

        pick_male.scaleX = 0.9f
        pick_male.scaleY = 0.9f
    }

}
