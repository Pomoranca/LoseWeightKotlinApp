package com.pomoranca.myapplication.activities.fragments


import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.mikepenz.iconics.Iconics.applicationContext

import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.SharedPref
import com.pomoranca.myapplication.activities.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.dialog_about.*
import kotlinx.android.synthetic.main.dialog_about.view.*
import kotlinx.android.synthetic.main.dialog_workout_finished.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment(), View.OnClickListener, OnAboutClickedListener{

    lateinit var sharedPref: SharedPref
    lateinit var rootView: View
    var listener : OnAboutClickedListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false)

        sharedPref = SharedPref(rootView.context)

        rootView.text_about.setOnClickListener(this)


        return rootView
    }

    override fun onClick(v: View?) {
        listener?.onAboutClicked()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.listener = context as OnAboutClickedListener
    }

    override fun onAboutClicked() {
    }




}
