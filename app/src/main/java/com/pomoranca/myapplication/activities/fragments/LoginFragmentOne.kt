package com.pomoranca.myapplication.activities.fragments


import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.data.User
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.fragment_login_one.*
import kotlinx.android.synthetic.main.fragment_login_one.view.*


/**
 * Login fragment that contains basic user info NAME and AVATAR
 */
class LoginFragmentOne : Fragment() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel
    private val PREFS_NAME = "MyPrefsFile"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login_one, container, false)

        val fragmentManager = activity!!.supportFragmentManager

        val settings: SharedPreferences =
            activity!!.getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode

        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)

        rootView.login_one_button_next.setOnClickListener {
            if (TextUtils.isEmpty(welcomeCardInput.text) || TextUtils.isDigitsOnly(welcomeCardInput.text)) {
                welcomeCardInput.error = "You don't have a name ? Come on"
            } else {
                val name = rootView.welcomeCardInput.text.toString().capitalize()
                val user = User(name, 0, 0)
                loseWeightViewModel.insert(user)
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                fragmentTransaction.replace(R.id.login_fragment_container, LoginFragmentTwo())
                fragmentTransaction.commit()
            }
        }

        rootView.pick_male.setOnClickListener {
            val settings: SharedPreferences =
                activity!!.getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode

            val editor = settings.edit()
            editor.putBoolean("pick_male", true)
            editor.apply()

            rootView.pick_male.scaleX = 1.3f
            rootView.pick_male.scaleY = 1.3f


            rootView.pick_female.scaleX = 0.9f
            rootView.pick_female.scaleY = 0.9f
        }
        rootView.pick_female.setOnClickListener {
            val settings: SharedPreferences =
                activity!!.getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode
//
            val editor = settings.edit()
            editor.putBoolean("pick_male", false)
            editor.apply()

            rootView.pick_female.scaleX = 1.3f
            rootView.pick_female.scaleY = 1.3f

            rootView.pick_male.scaleX = 0.9f
            rootView.pick_male.scaleY = 0.9f
        }

        return rootView
    }
}