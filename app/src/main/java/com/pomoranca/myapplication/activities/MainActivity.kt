package com.pomoranca.myapplication.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.activities.fragments.MainFragment
import com.pomoranca.myapplication.activities.fragments.ProfileFragment
import com.pomoranca.myapplication.activities.fragments.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val WORKOUT_PLAN = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.background = resources.getDrawable(R.color.colorRoutine)

        // Create the AccountHeader
        val headerResult = AccountHeaderBuilder()
            .withHeaderBackground(R.drawable.drawer_header_background)
            .withTranslucentStatusBar(true)
            .withActivity(this)
            .build()

        val homeItem = PrimaryDrawerItem().withIdentifier(1).withName("Home")
        val profileItem = PrimaryDrawerItem().withIdentifier(1).withName("My Profile")
        val calendarItem = PrimaryDrawerItem().withIdentifier(1).withName("Calendar")
        val settingsItem = SecondaryDrawerItem().withIdentifier(2).withName("Settings")


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
                                .commit()
                            return false
                        }
                        4 -> {
                            Toast.makeText(
                                this@MainActivity,
                                "Clicked  $position",
                                Toast.LENGTH_SHORT
                            ).show()
                            return true
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
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.profile_nav -> {
                val fragment = ProfileFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.settings_nav -> {
                val fragment = SettingsFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}
