package com.coffetime.cors.activities

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.coffetime.cors.NotificationReceiver
import com.coffetime.cors.R
import com.coffetime.cors.SharedPref
import com.coffetime.cors.activities.fragments.*
import com.coffetime.cors.data.Reminders
import com.coffetime.cors.data.User
import com.coffetime.cors.viewmodels.LoseWeightViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_welcome.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(),
    OnTimeSetListener,
    MotionLayout.TransitionListener {

    private lateinit var sharedPref: SharedPref
    private lateinit var alarmManager: AlarmManager
    private val BACK_STACK_ROOT_TAG = "root_fragment"
    private var sensorManager: SensorManager? = null
    private lateinit var daysText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var loseWeightViewModel: LoseWeightViewModel
    private var CURRENT_DATE = ""
    private var running = false
    private val motionLayout by lazy {
        findViewById<MotionLayout>(R.id.motionLayout)
    }


    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
    }

    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
    }

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
        //do something on finish animation

    }


    private val PREFS_NAME = "MyPrefsFile"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_main)

        //Insert user intodatabase
        sharedPref = SharedPref(this)
        val name = sharedPref.loadUserName()
        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        loseWeightViewModel.getAllUsers().observe(this, androidx.lifecycle.Observer {
            progressBar.progress = it[0].days
            daysText.text = "${it[0].days} / 100"
        })
        loseWeightViewModel.insert(User(name!!, 0, 0))


        motionLayout.setTransitionListener(this)

        alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        checkFirstTimeRun()

        //instance of Reminders class and generating random tips
        val reminderList = Reminders()
        textview_tips.text = reminderList.reminders.random()


        daysText = findViewById(R.id.fragment_main_text_days)
        progressBar = findViewById(R.id.fragment_main_progress_day)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        CURRENT_DATE = getDate()



        main_fab_calendar.setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
        }


        navigationView.setOnNavigationItemSelectedListener(navListener)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MainFragment())
            .commit()
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.home_nav -> {
                val fragment = MainFragment()
                supportFragmentManager.popBackStack(
                    BACK_STACK_ROOT_TAG,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.meal_nav -> {
                val fragment = MealTipsFragment()
                supportFragmentManager.popBackStack(
                    BACK_STACK_ROOT_TAG,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit()

                return@OnNavigationItemSelectedListener true
            }

            R.id.profile_nav -> {
                val fragment = ProfileFragment()
                supportFragmentManager.popBackStack(
                    BACK_STACK_ROOT_TAG,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.settings_nav -> {
                val fragment = SettingsFragment()
                supportFragmentManager.popBackStack(
                    BACK_STACK_ROOT_TAG,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    private fun showDialog() {
        val dialog = Dialog(this, R.style.ThemeDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_welcome)
        dialog.window?.setWindowAnimations(R.style.dialog_slide_out)
        val lp = dialog.window!!.attributes
        lp.dimAmount = 0.7f
        dialog.dialog_welcome_name.text = "Welcome"
        dialog.dialog_button_lets_start.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun checkFirstTimeRun() {
        sharedPref = SharedPref(this)

        val formatter = SimpleDateFormat("dd/MM/yyyy")
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        val current_date = formatter.format(calendar.time)
        val settings = getSharedPreferences(PREFS_NAME, 0)
        val editor = settings.edit()
        val firstTimeRun = settings.getBoolean("isFirstRun", true)
        if (firstTimeRun) {
            editor.putString("CURRENT_DATE", current_date)
            editor.putBoolean("isFirstRun", false)
            editor.apply()
            showDialog()
        } else {
            if (sharedPref.loadNightModeState()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }


    /******************************* ALERT TIALOG ********************************************/
    private fun startAlarm(c: Calendar) {


        val alarmManager: AlarmManager =
            getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, NotificationReceiver::class.java)

        val pendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 1, intent, FLAG_UPDATE_CURRENT)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, c.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)

        Snackbar.make(
            findViewById(android.R.id.content),
            "Reminder set !",
            Snackbar.LENGTH_LONG
        ).setBackgroundTint(resources.getColor(R.color.lightGreen))
            .show()


    }


    private fun getTime() {
        val c = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            //            c.set(Calendar.DAY_OF_YEAR, 1)
            c.set(Calendar.HOUR_OF_DAY, hour)
            c.set(Calendar.MINUTE, minute)
            c.set(Calendar.SECOND, 0)
            c.set(Calendar.MILLISECOND, 0)
            startAlarm(c)

            //save alarm in sharedprefs and update time text
            val a = SimpleDateFormat("HH:mm").format(c.timeInMillis)
            sharedPref.saveNotificationTime(a)
            sharedPref.saveNotificationState(true)
            restartSettingsFragment()

        }

        TimePickerDialog(
            this,
            R.style.ThemeCustomDialog,
            timeSetListener,
            c.get(Calendar.HOUR_OF_DAY),
            c.get(Calendar.MINUTE),
            true
        ).show()
    }


    override fun onTimeSet() {
        getTime()
    }

    private fun restartSettingsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SettingsFragment())
            .commit()
    }


    override fun onPause() {
        super.onPause()
        running = false

    }


    private fun getDate(): String { // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        return formatter.format(calendar.time)
    }


}






