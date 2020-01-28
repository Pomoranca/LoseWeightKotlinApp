package com.pomoranca.myapplication.activities.fragments


import android.content.Intent
import android.content.SharedPreferences
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.activities.AwardActivity
import com.pomoranca.myapplication.adapters.AwardRecyclerViewAdapter
import com.pomoranca.myapplication.adapters.AwardRecyclerViewAdapter.Companion.awardsList
import com.pomoranca.myapplication.data.Award
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlin.properties.Delegates
import kotlin.system.exitProcess

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel
    private var medalsWon = 0
    private val PREFS_NAME = "MyPrefsFile"
    var experience = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        //Populate USER OVERVIEW from DATABASE DATA
        loseWeightViewModel.getAllUsers().observe(this, Observer {
            profile_text_user_name.text = it[0].name
            profile_days_text.text = "${it[0].days}"
            profile_experience_text.text = "${it[0].experience}"



            for (i in awardsList) {
                if(i.won) {
                    medalsWon++
                }
            }

            profile_medals_text.text = "$medalsWon"

        })

        val settings: SharedPreferences =
            context!!.getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode
        val avatarMale = settings.getBoolean("pick_male", true)
        rootView.profile_steps_text.text = settings.getFloat("stepsMadeTotal", 0F).toInt().toString()


        //Question variables for profile information
        val questionOne = settings.getString("Q 0", "")
        val questionTwo = settings.getString("Q 1", "")
        val loginDate = settings.getString("CURRENT_DATE","")

        rootView.profile_text_question_one.text = questionOne
        rootView.profile_text_question_two.text = questionTwo
        rootView.profile_text_date_joined.text = loginDate


        if (avatarMale) {
            rootView.profile_image.setImageResource(R.drawable.male_pick_ful_size)
        } else {
            rootView.profile_image.setImageResource(R.drawable.female_pick_full_size)
        }

        val recyclerView = rootView.recycler_view_awards
        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val awardRecyclerViewAdapter = AwardRecyclerViewAdapter()
        recyclerView.adapter = awardRecyclerViewAdapter

        awardRecyclerViewAdapter.setOnItemClickListener(object :
            AwardRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(award: Award) {
                val intent = Intent(activity, AwardActivity::class.java)
                intent.putExtra("NAME", award.name)
                intent.putExtra("DESC", award.desc)
                intent.putExtra("BACKGROUND", award.imagePath)
                startActivity(intent)
            }
        })

        return rootView
    }




}
