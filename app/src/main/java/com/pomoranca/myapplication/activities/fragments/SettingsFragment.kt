package com.pomoranca.myapplication.activities.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.activities.listeners.OnAboutClickedListener
import kotlinx.android.synthetic.main.fragment_settings.view.*


/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment(), View.OnClickListener,
    OnAboutClickedListener {

    lateinit var rootView: View
    var listener: OnAboutClickedListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false)

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            activity!!.setTheme(R.style.CustomDarkTheme)
        } else {
            activity!!.setTheme(R.style.AppTheme)
        }
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            rootView.switch_narration.isChecked = true
        }
        rootView.switch_narration.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                val currentFragment = activity!!.supportFragmentManager.findFragmentById(R.id.fragment_container)
                activity!!.supportFragmentManager.beginTransaction()
                    .detach(currentFragment!!)
                    .attach(currentFragment!!)
                    .commit()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
        }
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
