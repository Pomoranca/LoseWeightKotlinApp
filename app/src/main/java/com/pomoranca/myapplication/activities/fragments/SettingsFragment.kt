package com.pomoranca.myapplication.activities.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Switch
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide

import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.SharedPref
import com.pomoranca.myapplication.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {

    lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)
        // Inflate the layout for this fragment
        sharedPref = SharedPref(rootView.context)



        if(sharedPref.loadNightTheme() == true) {
            context?.setTheme(R.style.DarkTheme)
        } else {
            context?.setTheme(R.style.AppTheme)
        }


        if(sharedPref.loadNightTheme() == true) {
            rootView.switch_theme.isChecked = true
        }

        rootView.switch_theme.setOnCheckedChangeListener { buttonView, isChecked ->
        if(isChecked) {
            sharedPref.setNightTheme(true)
            restartApp()
        } else {
            sharedPref.setNightTheme(false)
            restartApp()
        }

        }



        return rootView
    }

    fun restartApp() {
        val tr = getFragmentManager()?.beginTransaction()?.detach(SettingsFragment())
        tr?.replace(R.id.fragment_container, SettingsFragment())
        tr?.commit()
    }



}
