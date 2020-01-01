package com.pomoranca.myapplication.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.adapters.PlanRecyclerViewAdapter
import com.pomoranca.myapplication.adapters.PlanRecyclerViewAdapter.OnItemClickListener
import com.pomoranca.myapplication.data.WorkoutPlan
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel

    companion object {
        const val WORKOUT_PLAN = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        //Populate USER OVERVIEW from DATABASE DATA
        loseWeightViewModel.getAllUsers().observe(this, Observer {
            experience_number.text = it[0].experience.toString()
        })

        recyclerViewPlan.layoutManager = LinearLayoutManager(this)
        recyclerViewPlan.setHasFixedSize(true)
        val planRecyclerViewAdapter = PlanRecyclerViewAdapter()
        planRecyclerViewAdapter.populatePlanList()
        recyclerViewPlan.adapter = planRecyclerViewAdapter

        planRecyclerViewAdapter.setOnItemClickListener(object :
            OnItemClickListener {
            override fun onItemClick(workoutPlan: WorkoutPlan) {
                val intent = Intent(this@MainActivity, WorkoutPlanActivity::class.java)
                intent.putExtra("NAME", workoutPlan.name)
                intent.putExtra("BACKGROUND", workoutPlan.backgroundPath)
                startActivityForResult(intent, WORKOUT_PLAN)
            }

        })


    }
}
