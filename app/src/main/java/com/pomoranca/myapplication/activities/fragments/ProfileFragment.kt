package com.pomoranca.myapplication.activities.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        //Populate USER OVERVIEW from DATABASE DATA
        loseWeightViewModel.getAllUsers().observe(this, Observer {
            profile_text_user_name.text = it[0].name
        })
        return rootView
    }


}
