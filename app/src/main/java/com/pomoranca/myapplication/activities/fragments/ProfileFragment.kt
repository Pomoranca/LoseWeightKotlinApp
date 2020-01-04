package com.pomoranca.myapplication.activities.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel
    private var userExperience = 0
    private var medalsWon = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        //Populate USER OVERVIEW from DATABASE DATA
        loseWeightViewModel.getAllUsers().observe(this, Observer {
            profile_text_user_name.text = it[0].name
            profile_days_text.text = "Days spent working out: ${it[0].days}"
            profile_experience_text.text = "Experience gained: ${it[0].experience}"
            userExperience = it[0].experience
            when (userExperience) {
                in 1..99 -> {
                    image_starter.visibility = View.VISIBLE
                    medalsWon = 1
                }
                in 100..200 -> {
                    image_starter.visibility = View.VISIBLE
                    image_achiever.visibility = View.VISIBLE
                    medalsWon = 2
                }
                in 201..500 -> {
                    image_starter.visibility = View.VISIBLE
                    image_achiever.visibility = View.VISIBLE
                    image_beast.visibility = View.VISIBLE
                    medalsWon = 3
                }
                in 501..800 -> {
                    image_starter.visibility = View.VISIBLE
                    image_achiever.visibility = View.VISIBLE
                    image_beast.visibility = View.VISIBLE
                    image_finisher.visibility = View.VISIBLE
                    medalsWon = 4
                }
                in 801..1000 -> {
                    image_starter.visibility = View.VISIBLE
                    image_achiever.visibility = View.VISIBLE
                    image_beast.visibility = View.VISIBLE
                    image_finisher.visibility = View.VISIBLE
                    image_best.visibility = View.VISIBLE
                    medalsWon = 5
                }
            }
            profile_medals_text.text = "Medals won: $medalsWon"

        })


        return rootView
    }


}
