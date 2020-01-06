package com.pomoranca.myapplication.activities.fragments


import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.activities.MainActivity
import com.pomoranca.myapplication.activities.WorkoutPlanActivity
import com.pomoranca.myapplication.adapters.PlanRecyclerViewAdapter
import com.pomoranca.myapplication.data.WorkoutPlan
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_welcome.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*


var behavior: CoordinatorLayout.Behavior<*>? = null

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel
    private var userExperience = 0
    private var medalsWon = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        // Inflate the layout for this fragment

        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        //Populate USER OVERVIEW from DATABASE DATA
        loseWeightViewModel.getAllUsers().observe(this, Observer {
            userExperience = it[0].experience
            when (userExperience) {
                in 1..99 -> medalsWon = 1
                in 100..200 -> medalsWon = 2
                in 201..500 -> medalsWon = 3
                in 501..800 -> medalsWon = 4
                in 801..1000 -> medalsWon = 5
            }
            experience_number.text = userExperience.toString()
            text_hi.text = "Hi ${it[0].name}"
            days_number.text = it[0].days.toString()
            medals_number.text = medalsWon.toString()

        })

        val recyclerView = rootView.recyclerViewPlan

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        val planRecyclerViewAdapter = PlanRecyclerViewAdapter()
        planRecyclerViewAdapter.populatePlanList()
        recyclerView.adapter = planRecyclerViewAdapter

        planRecyclerViewAdapter.setOnItemClickListener(object :
            PlanRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(workoutPlan: WorkoutPlan) {
                val intent = Intent(activity, WorkoutPlanActivity::class.java)
                intent.putExtra("NAME", workoutPlan.name)
                intent.putExtra("BACKGROUND", workoutPlan.backgroundPath)
                startActivityForResult(intent, MainActivity.WORKOUT_PLAN)
            }
        })
        return rootView
    }



}

