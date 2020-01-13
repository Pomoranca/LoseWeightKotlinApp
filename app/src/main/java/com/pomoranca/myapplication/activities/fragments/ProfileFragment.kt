package com.pomoranca.myapplication.activities.fragments


import android.content.Intent
import android.content.SharedPreferences
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.activities.AwardActivity
import com.pomoranca.myapplication.adapters.AwardRecyclerViewAdapter
import com.pomoranca.myapplication.data.Award
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel
    private var userExperience = 0
    private var medalsWon = 0
    private val PREFS_NAME = "MyPrefsFile"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        val settings: SharedPreferences = context!!.getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode
        val avatarMale = settings.getBoolean("pick_male", true)

        if(avatarMale) {
            rootView.profile_image.setImageResource(R.drawable.male_pick_ful_size)
        } else {
            rootView.profile_image.setImageResource(R.drawable.female_pick_full_size)
        }

        val recyclerView = rootView.recycler_view_awards
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val awardRecyclerViewAdapter = AwardRecyclerViewAdapter()
        awardRecyclerViewAdapter.populateAwardList()
        recyclerView.adapter = awardRecyclerViewAdapter

        awardRecyclerViewAdapter.setOnItemClickListener(object : AwardRecyclerViewAdapter.OnItemClickListener{
            override fun onItemClick(award: Award) {
                val intent = Intent(activity, AwardActivity::class.java)
                intent.putExtra("NAME", award.name)
                intent.putExtra("DESC", award.desc)
                intent.putExtra("BACKGROUND", award.imagePath)
                startActivity(intent)
            }
        })





        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        //Populate USER OVERVIEW from DATABASE DATA
        loseWeightViewModel.getAllUsers().observe(this, Observer {
            profile_text_user_name.text = it[0].name
            profile_days_text.text = "Days spent working out: ${it[0].days}"
            profile_experience_text.text = "Experience gained: ${it[0].experience}"
            userExperience = it[0].experience
//
//
//
//            when (userExperience) {
//                in 1..99 -> {
//                    image_starter.visibility = View.VISIBLE
//                    medalsWon = 1
//                }
//                in 100..200 -> {
//                    image_starter.visibility = View.VISIBLE
//                    image_achiever.visibility = View.VISIBLE
//                    medalsWon = 2
//                }
//                in 201..500 -> {
//                    image_starter.visibility = View.VISIBLE
//                    image_achiever.visibility = View.VISIBLE
//                    image_beast.visibility = View.VISIBLE
//                    medalsWon = 3
//                }
//                in 501..800 -> {
//                    image_starter.visibility = View.VISIBLE
//                    image_achiever.visibility = View.VISIBLE
//                    image_beast.visibility = View.VISIBLE
//                    image_finisher.visibility = View.VISIBLE
//                    medalsWon = 4
//                }
//                in 801..1000 -> {
//                    image_starter.visibility = View.VISIBLE
//                    image_achiever.visibility = View.VISIBLE
//                    image_beast.visibility = View.VISIBLE
//                    image_finisher.visibility = View.VISIBLE
//                    image_best.visibility = View.VISIBLE
//                    medalsWon = 5
//                }
//            }
            profile_medals_text.text = "Medals won: $medalsWon"

        })


        return rootView
    }


}
