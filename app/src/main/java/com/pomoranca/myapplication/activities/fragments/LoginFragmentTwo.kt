package com.pomoranca.myapplication.activities.fragments


import android.app.ActionBar
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.activities.MainActivity
import com.pomoranca.myapplication.adapters.LoginQuestionRecyclerViewAdapter
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.fragment_login_two.view.*


/**
 * Login fragment that contains questions about user
 */
class LoginFragmentTwo : Fragment() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel
    private val PREFS_NAME = "MyPrefsFile"
    var name = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login_two, container, false)
        val settings: SharedPreferences = activity!!.getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode
        val editor = settings.edit()
        val hasLoggedIn = settings.getBoolean("hasLoggedIn", false)


        //init expanding recyclerview
        val recyclerView = rootView.login_two_recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)

        recyclerView.setHasFixedSize(true)
        val loginQuestionRecyclerViewAdapter = LoginQuestionRecyclerViewAdapter()
        loginQuestionRecyclerViewAdapter.populateQuestionList()
        recyclerView.adapter = loginQuestionRecyclerViewAdapter

        rootView.login_fragment_two_button_confirm.setOnClickListener {
                val intent = Intent(activity, MainActivity::class.java)
                editor.putBoolean("hasLoggedIn", true)
                // Commit the edits!
                editor.apply()
                startActivity(intent)
        }

        return rootView

        }



    }



