package com.pomoranca.myapplication.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.activities.fragments.MainFragment
import com.pomoranca.myapplication.activities.listeners.OnAboutClickedListener
import com.pomoranca.myapplication.activities.fragments.ProfileFragment
import com.pomoranca.myapplication.activities.fragments.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_about.*
import kotlinx.android.synthetic.main.dialog_welcome.*
import kotlinx.android.synthetic.main.dialog_welcome.dialog_welcome_name


class MainActivity : AppCompatActivity(),
    OnAboutClickedListener {

    companion object {
        const val WORKOUT_PLAN = 1
    }
    private val PREFS_NAME = "MyPrefsFile"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//        toolbar.setOnMenuItemClickListener {
//            startActivity(Intent(this, CalendarActivity::class.java))
//            return@setOnMenuItemClickListener false
//        }
        checkFirstTimeRun()

        main_fab_calendar.setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
        }


        // Create the AccountHeader
        val headerResult = AccountHeaderBuilder()
            .withPaddingBelowHeader(false)
            .withOnAccountHeaderListener(object : AccountHeader.OnAccountHeaderListener {
                override fun onProfileChanged(view: View?, profile: IProfile<*>, current: Boolean): Boolean {
                    return false
                }
            })
            .withHeaderBackground(R.drawable.drawer_header_logo)
            .withHeaderBackgroundScaleType(ImageView.ScaleType.FIT_CENTER)
//            .withTranslucentStatusBar(true)
            .withActivity(this)
            .withDividerBelowHeader(false)
            .build()

        val homeItem = PrimaryDrawerItem().withIdentifier(1).withName("Home").withIcon(R.drawable.home_ico)
        val profileItem = PrimaryDrawerItem().withIdentifier(1).withName("My Profile").withIcon(R.drawable.profile_ico)
        val calendarItem = PrimaryDrawerItem().withIdentifier(1).withName("Calendar").withIcon(R.drawable.ico_calendar_blk)
        val settingsItem = PrimaryDrawerItem().withIdentifier(1).withName("Settings").withIcon(R.drawable.settings_ico)


//create the drawer and remember the `Drawer` result object
        val result = DrawerBuilder()
            .withActivity(this)
            .withToolbar(toolbar)
            .withAccountHeader(headerResult)
            .addDrawerItems(
                homeItem,
                profileItem
                , settingsItem,
                calendarItem
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
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                    .addToBackStack(null)
                    .commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.profile_nav -> {
                val fragment = ProfileFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                    .addToBackStack(null)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.settings_nav -> {
                val fragment = SettingsFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                    .addToBackStack(null)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.action_bar_menu, menu)
//        return true
//    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_welcome)
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window?.setLayout(width,height)
        dialog.dialog_welcome_name.text = "Welcome"
//        val textView = view.findViewById<TextView>(R.id.dialog_welcome_name)
//        textView.text= "Welcome $userName"
        dialog.dialog_button_lets_start.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun checkFirstTimeRun() {
        val settings = getSharedPreferences(PREFS_NAME, 0)
        val firstTimeRun = settings.getBoolean("isFirstRun", true)
        if (firstTimeRun) {
            showDialog()
        }
        val editor = settings.edit()
        editor.putBoolean("isFirstRun", false)
        editor.apply()
    }

    private fun showAboutDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_about)
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window?.setLayout(width,height)
        dialog.dialog_about_button_close.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onAboutClicked() {
        showAboutDialog()
    }


}
