package com.pomoranca.myapplication.activities.fragments


import android.app.ActivityOptions
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.pomoranca.myapplication.NotificationReceiver
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.SharedPref
import com.pomoranca.myapplication.activities.MainActivity
import com.pomoranca.myapplication.activities.listeners.OnAboutClickedListener
import kotlinx.android.synthetic.main.activity_main.*
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
//                val i = Intent(activity, MainActivity::class.java)
//                if (Build.VERSION.SDK_INT > 20) {
//                    val options =
//                        ActivityOptions.makeSceneTransitionAnimation(activity)
//                    startActivity(i, options.toBundle())
//                } else {
//                    startActivity(i)
//                }
                startActivity(Intent(rootView.context, MainActivity::class.java))
                activity!!.finish()

            } else {
                sharedPref.saveNightModeState(false)
//                val i = Intent(activity, MainActivity::class.java)
//                if (Build.VERSION.SDK_INT > 20) {
//                    val options =
//                        ActivityOptions.makeSceneTransitionAnimation(activity)
//                    startActivity(i, options.toBundle())
//                } else {
//                    startActivity(i)
//                }
//            }
                startActivity(Intent(rootView.context, MainActivity::class.java))
                activity!!.finish()

            }
        }
        //edit notification settings
        rootView.switch_notifications.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                sharedPref.saveNotificationState(true)
                rootView.settings_button_timepicker.visibility = View.VISIBLE
                timeSetListener?.onTimeSet()

            } else {
                sharedPref.saveNotificationState(false)
                cancelAlarm()
                rootView.settings_button_timepicker.visibility = View.GONE
            }
        }

        rootView.settings_button_timepicker.setOnClickListener {
            timeSetListener?.onTimeSet()
        }

        updateTime()
        rootView.text_about.setOnClickListener(this)


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
        ).setBackgroundTint(activity!!.resources.getColor(R.color.startTimer))
            .show()
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


    fun updateTime() {
        rootView.settings_text_time.text = sharedPref.loadNotificationTime()

    }



}

    interface OnTimeSetListener {
        fun onTimeSet()
    }


