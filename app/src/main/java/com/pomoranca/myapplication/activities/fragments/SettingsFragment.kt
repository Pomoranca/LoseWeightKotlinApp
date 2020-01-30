package com.pomoranca.myapplication.activities.fragments


import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.SharedPref
import com.pomoranca.myapplication.activities.MainActivity
import com.pomoranca.myapplication.activities.listeners.OnAboutClickedListener
import kotlinx.android.synthetic.main.fragment_settings.view.*


/**
 * Settomgs Fragment
 */
class SettingsFragment : Fragment(), View.OnClickListener,
    OnAboutClickedListener, OnTimeSetListener {

    lateinit var sharedPref: SharedPref

    lateinit var alarmManager: AlarmManager
    lateinit var rootView: View
    var listenerAbout: OnAboutClickedListener? = null
    var timeSetListener: OnTimeSetListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Save inflated settings fragment in a value
        rootView = inflater.inflate(R.layout.fragment_settings, container, false)

        alarmManager = activity!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager


        //declare settings variable
        sharedPref = SharedPref(rootView.context)

        //load theme settings
        if (sharedPref.loadNightModeState()) {
            rootView.switch_theme.isChecked = true
        }
        //load notification settings
        if (sharedPref.loadNotificationState()) {
            rootView.switch_notifications.isChecked = true
        }

        //edit theme settings
        rootView.switch_theme.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                sharedPref.saveNightModeState(true)
                startActivity(Intent(rootView.context, MainActivity::class.java))
            } else {
                sharedPref.saveNightModeState(false)
                startActivity(Intent(rootView.context, MainActivity::class.java))
            }
        }
        //edit notification settings
        rootView.switch_notifications.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                sharedPref.saveNotificationState(true)

            } else {
                sharedPref.saveNotificationState(false)
            }
        }

        rootView.settings_button_timepicker.setOnClickListener {
            timeSetListener?.onTimeSet()
        }

        updateTime()
        rootView.text_about.setOnClickListener(this)


        return rootView
    }


    override fun onClick(v: View?) {
        listenerAbout?.onAboutClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.listenerAbout = context as OnAboutClickedListener
        this.timeSetListener = context as OnTimeSetListener
    }

    override fun onAboutClicked() {
    }


    override fun onTimeSet() {
    }
    fun updateTime() {
        rootView.settings_text_time.text = sharedPref.loadNotificationTime()

    }
}

interface OnTimeSetListener {
    fun onTimeSet()
}



