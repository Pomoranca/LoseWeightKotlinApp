package com.pomoranca.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.adapters.ItemRecyclerViewAdapter
import com.pomoranca.myapplication.data.Workout
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.activity_workout_plan.*

class WorkoutPlanActivity : AppCompatActivity() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel

    companion object {
        const val EXTRA_TITLE = "com.pomoranca.myapplication.activities.EXTRA_TITLE"
        const val EXTRA_MEDALS = "com.pomoranca.myapplication.activities.EXTRA_MEDALS"
        const val EXTRA_WORKOUTS = "com.pomoranca.myapplication.activities.EXTRA_WORKOUTS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_plan)
        val planTitle = intent.getStringExtra("NAME")
        val backgroundPath = intent.getIntExtra("BACKGROUND", 0)

        title = planTitle
        Glide
            .with(this)
            .load(backgroundPath)
            .centerCrop()
            .into(startWorkoutBackground)

        workoutRecyclerView.layoutManager = LinearLayoutManager(this)
        workoutRecyclerView.setHasFixedSize(true)
        val adapter = ItemRecyclerViewAdapter()
        workoutRecyclerView.adapter = adapter

        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        //POPULATE recyclerview regarding which plan is clicked
        when(planTitle) {
            "Beginner plan" -> loseWeightViewModel.getBeginnerWorkouts().observe(this,
                Observer<List<Workout>> {
                    adapter.submitList(it)
                })
            "Intermediate plan" -> loseWeightViewModel.getIntermediateWorkouts().observe(this,
                Observer<List<Workout>> {
                    adapter.submitList(it)
                })
            "Advanced plan" -> loseWeightViewModel.getAdvancedWorkouts().observe(this,
                Observer<List<Workout>> {
                    adapter.submitList(it)
                })
            "Premium plan" -> loseWeightViewModel.getAllWorkouts().observe(this,
                Observer<List<Workout>> {
                    adapter.submitList(it)
                })
        }
        textViewStartWorkout.setOnClickListener {
            val intent: Intent = Intent(this, WorkoutActivity::class.java)
            intent.putExtra("PLAN_TITLE", planTitle)
            startActivity(intent)
        }
    }
}
