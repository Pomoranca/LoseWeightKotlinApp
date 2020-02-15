package com.coffetime.cors.activities.fragments


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.coffetime.cors.NotificationReceiver
import com.coffetime.cors.R
import com.coffetime.cors.SharedPref
import com.coffetime.cors.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_settings.view.*


/**
 * Settomgs Fragment
 */
class SettingsFragment : Fragment(),
    OnTimeSetListener {

    private lateinit var sharedPref: SharedPref

    private lateinit var alarmManager: AlarmManager
    private lateinit var rootView: View
    private var timeSetListener: OnTimeSetListener? = null


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
        //load narration settings
        if (sharedPref.loadNarrationState()) {
            rootView.switch_narration.isChecked = true
        }


        //edit theme settings
        rootView.switch_theme.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                sharedPref.saveNightModeState(true)
//
                activity!!.finish()
                startActivity(Intent(rootView.context, MainActivity::class.java))


            } else {
                sharedPref.saveNightModeState(false)
                activity!!.finish()
                startActivity(Intent(rootView.context, MainActivity::class.java))


            }
        }
        //edit narration settings
        rootView.switch_narration.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sharedPref.saveNarrationState(true)
            } else {
                sharedPref.saveNarrationState(false)
            }
        }


        //edit notification settings
        rootView.switch_notifications.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                sharedPref.saveNotificationState(true)
                timeSetListener?.onTimeSet()

            } else {
                sharedPref.saveNotificationState(false)
                cancelAlarm()
            }
        }

        updateTime()

        return rootView
    }

    override fun onTimeSet() {
    }

    private fun cancelAlarm() {
        val intent = Intent(rootView.context, NotificationReceiver::class.java)
        val pendingIntent: PendingIntent =
            PendingIntent.getBroadcast(rootView.context, 0, intent, 0)
        alarmManager.cancel(pendingIntent)
        Snackbar.make(
            activity!!.findViewById(android.R.id.content),
            "Reminder canceled !",
            Snackbar.LENGTH_LONG
        ).setBackgroundTint(activity!!.resources.getColor(R.color.lightGreen))
            .show()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.timeSetListener = context as OnTimeSetListener
    }



    private fun updateTime() {
        rootView.settings_text_time.text = sharedPref.loadNotificationTime()

    }


}


interface OnTimeSetListener {
    fun onTimeSet()
}


