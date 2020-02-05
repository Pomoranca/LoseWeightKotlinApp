package com.pomoranca.myapplication.activities.fragments


import android.animation.ObjectAnimator
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
    lateinit var userText: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login_one, container, false)
        val settings: SharedPreferences =
            activity!!.getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode
//
        val editor = settings.edit()

        userText = rootView.findViewById(R.id.welcomeCardInput)

        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        doBounceAnimation(rootView.login_background)
        rootView.login_one_button_confirm.setOnClickListener {
            if (TextUtils.isEmpty(welcomeCardInput.text) || TextUtils.isDigitsOnly(welcomeCardInput.text)) {
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    "Please enter your name !",
                    Snackbar.LENGTH_LONG
                ).setBackgroundTint(activity!!.resources.getColor(R.color.lightGreen))
                    .show()
            } else {
                rootView.pick_male.visibility = View.VISIBLE
                rootView.pick_female.visibility = View.VISIBLE
                rootView.login_one_button_confirm.visibility = View.INVISIBLE
                rootView.welcomeCardInput.visibility = View.INVISIBLE
                doBounceAnimation(rootView.pick_male)
                doBounceAnimation(rootView.pick_female)
            }
        }


        rootView.pick_male.setOnClickListener {
            rootView.pick_female.isClickable = false
            editor.putBoolean("pick_male", true)
            editor.apply()
            rootView.pick_male.animate().alpha(0f).setDuration(500)
                .withEndAction {
                    next()
                }


        }
        rootView.pick_female.setOnClickListener {
            rootView.pick_male.isClickable = false
            editor.putBoolean("pick_male", false)
            editor.apply()
            rootView.pick_female.animate().alpha(0f).setDuration(500)
                .withEndAction {
                    next()
                }
        }



        return rootView

    }

    fun next() {
        val name = welcomeCardInput.text.toString().capitalize()


        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val transaction =
            fragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter,
            R.anim.exit,
            R.anim.pop_enter,
            R.anim.pop_exit
        )
        val bundle = Bundle()
        bundle.putString("name", name)
        val fragment = LoginFragmentTwo()
        fragment.arguments = bundle

        transaction.replace(R.id.login_fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()


//        val fragmentTransaction = fragmentManager!!.beginTransaction()
//        fragmentTransaction.setCustomAnimations(
//            R.anim.enter_from_right,
//            R.anim.exit_to_right
//        )
//        fragmentTransaction.replace(R.id.login_fragment_container, LoginFragmentTwo())
//        fragmentTransaction.commit()

    }


    private fun doBounceAnimation(targetView: View) {
        val animator = ObjectAnimator.ofFloat(targetView, "translationX", 300f, 0f)
        animator.interpolator = BounceInterpolator()
        animator.duration = 1000
        animator.start()
    }


}