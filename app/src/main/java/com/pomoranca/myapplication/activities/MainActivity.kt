package com.pomoranca.myapplication.activities

import android.app.*
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_about.*
import kotlinx.android.synthetic.main.dialog_welcome.*
import kotlinx.android.synthetic.main.dialog_welcome.dialog_welcome_name
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(),
    OnAboutClickedListener, OnTimeSetListener {

    lateinit var video: VideoView
    private lateinit var sharedPref: SharedPref
    lateinit var notificationManager: NotificationManager
    lateinit var alarmManager: AlarmManager
    private val BACK_STACK_ROOT_TAG = "root_fragment"

    private val PREFS_NAME = "MyPrefsFile"


    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref = SharedPref(this)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (!sharedPref.loadNightModeState()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        video = findViewById(R.id.header_image)
        alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        checkFirstTimeRun()
        showImage()


        main_fab_calendar.setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
        }
        // Create the AccountHeader
        val headerResult = AccountHeaderBuilder()
            .withPaddingBelowHeader(false)
            .withOnAccountHeaderListener(object : AccountHeader.OnAccountHeaderListener {
                override fun onProfileChanged(
                    view: View?,
                    profile: IProfile<*>,
                    current: Boolean
                ): Boolean {
                    return false
                }
            })
            .withHeaderBackground(R.drawable.drawer_header_logo)
            .withHeaderBackgroundScaleType(ImageView.ScaleType.FIT_CENTER)
            .withActivity(this)
            .withDividerBelowHeader(true)
            .build()

        val homeItem =
            PrimaryDrawerItem().withIdentifier(1).withName("Plan").withIcon(R.drawable.home_ico)
        val profileItem = PrimaryDrawerItem().withIdentifier(1).withName("Profile")
            .withIcon(R.drawable.profile_ico)
        val calendarItem = PrimaryDrawerItem().withIdentifier(1).withName("Calendar")
            .withIcon(R.drawable.ico_calendar_blk)
        val settingsItem = PrimaryDrawerItem().withIdentifier(1).withName("Settings")
            .withIcon(R.drawable.settings_ico)
        val aboutItem = PrimaryDrawerItem().withIdentifier(1).withName("About")

//create the drawer and remember the `Drawer` result object
        DrawerBuilder()
            .withActivity(this)
            .withToolbar(toolbar)
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

    private fun setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            val fade = Fade()
            fade.interpolator = android.view.animation.LinearInterpolator()
            fade.duration = 1000
            window.exitTransition = fade
            window.enterTransition = fade
        }
    }


    private fun showDialog() {
        val dialog = Dialog(this, R.style.ThemeCustomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_welcome)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setWindowAnimations(R.style.dialog_slide_out)
        dialog.dialog_welcome_name.text = "Welcome"
        dialog.dialog_button_lets_start.setOnClickListener {
            //            showVideo()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun checkFirstTimeRun() {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        val CURRENT_DATE = formatter.format(calendar.time)
        val settings = getSharedPreferences(PREFS_NAME, 0)
        val editor = settings.edit()
        val firstTimeRun = settings.getBoolean("isFirstRun", true)
        if (firstTimeRun) {
            showDialog()
            editor.putString("CURRENT_DATE", CURRENT_DATE)
//        } else {
//            showImage()
        }
        editor.putBoolean("isFirstRun", false)
        editor.apply()
    }

    private fun showAboutDialog() {
        val dialog = Dialog(this, R.style.ThemeCustomDialog)
        dialog.setContentView(R.layout.dialog_about)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            if (!sharedPref.loadNightModeState()) {
                Glide
                    .with(this@MainActivity)
                    .load(R.drawable.version_logo_light)
                    .into(dialog.dialog_about_image)
                val myFadeInAnimation: Animation =
                    AnimationUtils.loadAnimation(this@MainActivity, R.anim.fadein)
                dialog.dialog_about_image.startAnimation(myFadeInAnimation)
            } else {
                Glide
                    .with(this@MainActivity)
                    .load(R.drawable.version_logo_dark)
                    .into(dialog.dialog_about_image)
                val myFadeInAnimation: Animation =
                    AnimationUtils.loadAnimation(this@MainActivity, R.anim.fadein)
                dialog.dialog_about_image.startAnimation(myFadeInAnimation)
            }

        dialog.window?.setWindowAnimations(R.style.dialog_slide)
        dialog.dialog_about_button_close.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onAboutClicked() {
        showAboutDialog()
    }


//    private fun showVideo() {
//        header_image.visibility = View.VISIBLE
//        val path = "android.resource://" + packageName + "/" + R.raw.appbar_background
//        video.setVideoPath(path)
//        video.requestFocus()
//        video.setOnPreparedListener {
//        }
//        video.setOnCompletionListener {
//            showImage()
//        }
//        video.start()
//    }

    private fun showImage() {
        if (!sharedPref.loadNightModeState()) {
            Glide
                .with(this)
                .load(R.drawable.main_appbar_image_light)
                .centerCrop()
                .into(header_fading_image)
            val fadingImage = findViewById<ImageView>(R.id.header_fading_image)
            val myFadeInAnimation: Animation =
                AnimationUtils.loadAnimation(this@MainActivity, R.anim.fadein)
            fadingImage.startAnimation(myFadeInAnimation)
        } else {
            Glide
                .with(this)
                .load(R.drawable.main_appbar_image_dark)
                .centerCrop()
                .into(header_fading_image)
            val fadingImage = findViewById<ImageView>(R.id.header_fading_image)
            val myFadeInAnimation: Animation =
                AnimationUtils.loadAnimation(this@MainActivity, R.anim.fadein)
            fadingImage.startAnimation(myFadeInAnimation)

        }

    }

    override fun onDestroy() {
        super.onDestroy()
//        video.stopPlayback()
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


}






