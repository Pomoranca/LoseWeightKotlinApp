package com.pomoranca.myapplication.activities.fragments


import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
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

        Glide.with(rootView.context)
            .load(R.drawable.login_background)
            .centerCrop()
            .into(rootView.login_background)


        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)


        rootView.pick_male.setOnClickListener {
            val settings: SharedPreferences =
                activity!!.getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode
            val editor = settings.edit()
            if (TextUtils.isEmpty(welcomeCardInput.text) || TextUtils.isDigitsOnly(welcomeCardInput.text)) {
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    "Please enter your name !",
                    Snackbar.LENGTH_LONG
                ).setBackgroundTint(activity!!.resources.getColor(R.color.startTimer))
                    .show()
            } else {
                rootView.pick_female.isClickable = false
                editor.putBoolean("pick_male", true)
                editor.apply()
                rootView.pick_male.animate().alpha(0f).setDuration(500)
                    .withEndAction {
                        next()
                    }
            }


        }
        rootView.pick_female.setOnClickListener {
            val settings: SharedPreferences =
                activity!!.getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode
//
            val editor = settings.edit()

            if (TextUtils.isEmpty(welcomeCardInput.text) || TextUtils.isDigitsOnly(welcomeCardInput.text)) {
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    "Please enter your name !",
                    Snackbar.LENGTH_LONG
                ).setBackgroundTint(activity!!.resources.getColor(R.color.startTimer))
                    .show()
            } else {
                rootView.pick_male.isClickable = false
                editor.putBoolean("pick_male", false)
                editor.apply()
                rootView.pick_female.animate().alpha(0f).setDuration(500)
                    .withEndAction {
                        next()
                    }
            }
        }



        return rootView

    }

    fun next() {
        val name = welcomeCardInput.text.toString().capitalize()
        val user = User(name, 0, 0)
        loseWeightViewModel.insert(user)
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_right,
            R.anim.enter_from_right,
            R.anim.exit_to_right
        )
        fragmentTransaction.replace(R.id.login_fragment_container, LoginFragmentTwo())
        fragmentTransaction.commit()

    }
}