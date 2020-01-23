package com.pomoranca.myapplication.activities.fragments


import android.content.Intent
import android.content.SharedPreferences
import android.hardware.SensorManager
import android.os.Bundle
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
        val settings: SharedPreferences =
            context!!.getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode
        val avatarMale = settings.getBoolean("pick_male", true)

        //Question variables for profile information
        val questionOne = settings.getString("Q 0", "")
        val questionTwo = settings.getString("Q 1", "")
//        Toast.makeText(context, "$a $b", Toast.LENGTH_LONG).show()

        if (avatarMale) {
            rootView.profile_image.setImageResource(R.drawable.male_pick_ful_size)
        } else {
            rootView.profile_image.setImageResource(R.drawable.female_pick_full_size)
        }

        val recyclerView = rootView.recycler_view_awards
        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val awardRecyclerViewAdapter = AwardRecyclerViewAdapter()
        awardRecyclerViewAdapter.populateAwardList()
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

        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        //Populate USER OVERVIEW from DATABASE DATA
        loseWeightViewModel.getAllUsers().observe(this, Observer {
            profile_text_user_name.text = it[0].name
            profile_days_text.text = "Days spent working out: ${it[0].days}"
            profile_experience_text.text = "Experience gained: ${it[0].experience}"
            userExperience = it[0].experience
//

            when (userExperience) {
                in 1..99 -> {
                   awardRecyclerViewAdapter.awardsList[0].won = true
                }
                in 100..200 -> {
                    awardRecyclerViewAdapter.awardsList[0].won = true
                    awardRecyclerViewAdapter.awardsList[1].won = true

                }
                in 201..500 -> {
                    awardRecyclerViewAdapter.awardsList[0].won = true
                    awardRecyclerViewAdapter.awardsList[1].won = true
                    awardRecyclerViewAdapter.awardsList[2].won = true

                }
                in 501..800 -> {
                    awardRecyclerViewAdapter.awardsList[0].won = true
                    awardRecyclerViewAdapter.awardsList[1].won = true
                    awardRecyclerViewAdapter.awardsList[2].won = true
                    awardRecyclerViewAdapter.awardsList[3].won = true

                }
                in 801..1000 -> {
                    awardRecyclerViewAdapter.awardsList[0].won = true
                    awardRecyclerViewAdapter.awardsList[1].won = true
                    awardRecyclerViewAdapter.awardsList[2].won = true
                    awardRecyclerViewAdapter.awardsList[3].won = true
                    awardRecyclerViewAdapter.awardsList[4].won = true

                }
            }
            profile_medals_text.text = "Medals won: $medalsWon"

        })


        return rootView
    }


}
