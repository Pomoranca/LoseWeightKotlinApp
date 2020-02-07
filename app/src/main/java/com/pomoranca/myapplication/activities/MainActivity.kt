package com.pomoranca.myapplication.activities

import android.app.*
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import android.os.Bundle
import android.transition.Fade
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import com.pomoranca.myapplication.NotificationReceiver
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.SharedPref
import com.pomoranca.myapplication.activities.fragments.*
import com.pomoranca.myapplication.activities.listeners.OnAboutClickedListener
import com.pomoranca.myapplication.data.Reminders
import com.pomoranca.myapplication.data.User
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_about.*
import kotlinx.android.synthetic.main.dialog_welcome.*
import kotlinx.android.synthetic.main.dialog_welcome.dialog_welcome_name
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(),
    OnAboutClickedListener, OnTimeSetListener,
    MotionLayout.TransitionListener {

    private lateinit var sharedPref: SharedPref
    private lateinit var alarmManager: AlarmManager
    private val BACK_STACK_ROOT_TAG = "root_fragment"
    private var sensorManager: SensorManager? = null
    private lateinit var stepCounterText: TextView
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

//    private fun doBounceAnimation(targetView: View) {
//        val animator = ObjectAnimator.ofFloat(targetView, "translationY", 0f, 30f, 0f)
//        animator.interpolator = BounceInterpolator()
//        animator.duration = 1000
//        animator.start()
//    }

    private val PREFS_NAME = "MyPrefsFile"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_main)
//        showImage()

        //Insert user intodatabase
        sharedPref = SharedPref(this)
        val name = sharedPref.loadUserName()
        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        loseWeightViewModel.getAllUsers().observe(this, androidx.lifecycle.Observer {
            progressBar.progress = it[0].days
            stepCounterText.text = "${it[0].days} / 100"
        })
        loseWeightViewModel.insert(User(name!!, 0, 0))


        motionLayout.setTransitionListener(this)

        alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        checkFirstTimeRun()

        //instance of Reminders class and generating random tips
        val reminderList = Reminders()
        textview_tips.text = reminderList.reminders.random()


        stepCounterText = findViewById(R.id.fragment_main_text_step_counter)
        progressBar = findViewById(R.id.fragment_main_progress_day)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        CURRENT_DATE = getDate()
//        getValues()


        main_fab_calendar.setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
        }
        // Create the AccountHeader
        val headerResult = AccountHeaderBuilder()
            .withPaddingBelowHeader(false)
            .withTranslucentStatusBar(true)
            .withOnAccountHeaderListener(object : AccountHeader.OnAccountHeaderListener {
                override fun onProfileChanged(
                    view: View?,
                    profile: IProfile<*>,
                    current: Boolean
                ): Boolean {
                    return false
                }
            })
            .withHeaderBackground(R.drawable.web_hi_res_512)
            .withHeaderBackgroundScaleType(ImageView.ScaleType.FIT_CENTER)
            .withActivity(this)
            .withDividerBelowHeader(true)
            .build()

        val homeItem =
            PrimaryDrawerItem().withIdentifier(1).withName("Plan").withIcon(R.drawable.home_ico)
        val profileItem = PrimaryDrawerItem().withIdentifier(1).withName("Profile")
            .withIcon(R.drawable.profile_ico)
        val calendarItem = PrimaryDrawerItem().withIdentifier(1).withName("Calendar")
            .withIcon(R.drawable.ico_calendar_dark)
        val settingsItem = PrimaryDrawerItem().withIdentifier(1).withName("Settings")
            .withIcon(R.drawable.settings_ico)
        val aboutItem = PrimaryDrawerItem().withIdentifier(1).withName("About")
            .withIcon(R.drawable.ic_about_dark)

        DrawerBuilder()
            .withActivity(this)
            .withAccountHeader(headerResult)
            .addDrawerItems(
                homeItem,
                profileItem
                , settingsItem,
                calendarItem,
                aboutItem
            ).withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    when (position) {
                        1 -> {
                            val fragment = MainFragment()
                            supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.fragment_container,
                                    fragment,
                                    fragment.javaClass.simpleName
                                )
                                .addToBackStack(null)
                                .commit()
                            return false
                        }
                        2 -> {
                            val fragment = ProfileFragment()
                            supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.fragment_container,
                                    fragment,
                                    fragment.javaClass.simpleName
                                )
                                .addToBackStack(null)
                                .commit()
                            return false
                        }
                        3 -> {
                            val fragment = SettingsFragment()
                            supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.fragment_container,
                                    fragment,
                                    fragment.javaClass.simpleName
                                )
                                .addToBackStack(null)
                                .commit()
                            return false
                        }
                        4 -> {
                            startActivity(Intent(this@MainActivity, CalendarActivity::class.java))
                            return false
                        }
                        5 -> {
                            showAboutDialog()
                        }

                    }
                    return false
                }


            })
            .build()

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
        lp.dimAmount = 0.9f
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
//            showImage()
        }

    }

    private fun showAboutDialog() {
        val dialog = Dialog(this, R.style.ThemeDialog)
        dialog.setContentView(R.layout.dialog_about)
        val myFadeInAnimation: Animation =
            AnimationUtils.loadAnimation(this@MainActivity, R.anim.fadein)
        dialog.dialog_about_image.startAnimation(myFadeInAnimation)
        dialog.show()
        dialog.window?.setWindowAnimations(R.style.dialog_slide)
        dialog.dialog_about_button_close.setOnClickListener {
            //            showImage()
            dialog.dismiss()

        }

    }

    override fun onAboutClicked() {
        showAboutDialog()
    }

//
//    private fun showImage() {
//        Glide
//            .with(this)
//            .load(R.drawable.main_background)
//            .centerCrop()
//            .into(backgroundView)
//        val fadingImage = findViewById<ImageView>(R.id.backgroundView)
//        val myFadeInAnimation: Animation =
//            AnimationUtils.loadAnimation(this@MainActivity, R.anim.fadein)
//        fadingImage.startAnimation(myFadeInAnimation)
//    }


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

//    override fun onResume() {
//        super.onResume()
//        running = true
//        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
//        if (stepSensor == null) {
//            fragment_main_text_step_counter.visibility = View.GONE
//            fragment_main_progress_step.visibility = View.GONE
//
//        } else {
//            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
//        }
//    }

    override fun onPause() {
        super.onPause()
        running = false
//        sensorManager?.unregisterListener(this)
//        saveValues()
    }

//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//    }
//
//    override fun onSensorChanged(event: SensorEvent?) {
//        val sensor: Sensor = event?.sensor!!
//        val values = event.values
//        var value = -1
//
//        if (values.isNotEmpty()) {
//            value = values[0].toInt()
//        }
//
//        if (sensor.type == Sensor.TYPE_STEP_DETECTOR) {
//            stepsMadeToday++
////        stepsMade = event!!.values[0]
//            stepCounterText.text = "${stepsMadeToday.toInt()} / 3000"
//            progressBar.progress = value
//            Log.i("SENSOR", "${event!!.values[0]}")
//        }
//    }

    private fun getDate(): String { // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        return formatter.format(calendar.time)
    }

//    private fun getValues() {
//        val settings: SharedPreferences =
//            getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode
//        val editor = settings.edit()
//        stepsMadeToday = settings.getFloat("stepsMadeToday", 0F)
//        stepCounterText.text = "${stepsMadeToday.toInt()} / 3000"
//        stepsMadeTotal = settings.getFloat("stepsMadeTotal", 0F)
//        LAST_DATE = settings.getString("lastDate", "")!!
//
//
//        if (LAST_DATE != CURRENT_DATE) {
//            stepsMadeToday = 0F
//            LAST_DATE = CURRENT_DATE
//            editor.putString("lastDate", LAST_DATE)
//            Log.i("steps", "DATES ARE NOT SAME")
//
//        }
//        stepCounterText.text = "${stepsMadeToday.toInt()} / 3000"
//        progressBar.progress = stepsMadeToday.toInt()
//        editor.apply()
//    }

//    private fun saveValues() {
//        val settings: SharedPreferences =
//            getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode
//        val editor = settings.edit()
//        stepsMadeTotal = settings.getFloat("stepsMadeTotal", 0F)
//        stepsMadeTotal += stepsMadeToday
//        Log.i("steps", "TODAY - $stepsMadeToday")
//        Log.i("steps", "TOTAL - $stepsMadeTotal")
//        Log.i("steps", "DATE - $CURRENT_DATE , $LAST_DATE")
//        editor.putFloat("stepsMadeTotal", stepsMadeTotal)
//        editor.putFloat("stepsMadeToday", stepsMadeToday)
//        editor.apply()
//    }


}






