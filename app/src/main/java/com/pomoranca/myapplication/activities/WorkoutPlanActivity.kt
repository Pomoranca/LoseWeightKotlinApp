package com.pomoranca.myapplication.activities

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.activities.WorkoutActivity.Companion.finalWorkoutList
import com.pomoranca.myapplication.adapters.ItemRecyclerViewAdapter
import com.pomoranca.myapplication.data.Workout
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.activity_workout_plan.*
import kotlinx.android.synthetic.main.dialog_how_to_workout.*


class WorkoutPlanActivity : AppCompatActivity() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel
    private val PREFS_NAME = "MyPrefsFile"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_plan)
        val planTitle = intent.getStringExtra("NAME")
        val settings: SharedPreferences = getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode
        val editor = settings.edit()

        val hasLoggedIn = settings.getBoolean("hasLoggedIn", false)

        
        setSupportActionBar(toolbar_workout_plan)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Workout plan"


        workoutRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        workoutRecyclerView.setHasFixedSize(true)
        val adapter = ItemRecyclerViewAdapter()
        workoutRecyclerView.adapter = adapter

        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        //POPULATE recyclerview regarding which plan is clicked
        when (planTitle) {
            "Beginner plan" -> loseWeightViewModel.getBeginnerWorkouts().observe(this,
                Observer<List<Workout>> {
                    adapter.workoutList = it as MutableList<Workout>
                    finalWorkoutList = it
                    adapter.notifyDataSetChanged()
//                    workout_plan_image_header.setImageResource(R.drawable.plan_beginner)
                })
            "Intermediate plan" -> loseWeightViewModel.getIntermediateWorkouts().observe(this,
                Observer<List<Workout>> {
                    adapter.workoutList = it as MutableList<Workout>
                    finalWorkoutList = it
//                    workout_plan_image_header.setImageResource(R.drawable.plan_intermediate)
                    adapter.notifyDataSetChanged()
                })
            "Advanced plan" -> loseWeightViewModel.getAdvancedWorkouts().observe(this,
                Observer<List<Workout>> {
                    adapter.workoutList = it as MutableList<Workout>
                    finalWorkoutList = it
//                    workout_plan_image_header.setImageResource(R.drawable.plan_advanced)
                    adapter.notifyDataSetChanged()
                })
            "Insane plan" -> loseWeightViewModel.getInsaneWorkouts().observe(this,
                Observer<List<Workout>> {
                    adapter.workoutList = it as MutableList<Workout>
                    finalWorkoutList = it
//                    workout_plan_image_header.setImageResource(R.drawable.plan_premium)
                    adapter.notifyDataSetChanged()
                })
        }
        checkFirstTimeWorkout()

        button_begin_workout.setOnClickListener {
            val intent = Intent(this, WorkoutActivity::class.java)
            intent.putExtra("PLAN_TITLE", planTitle)
            startActivity(intent)
        }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_how_to_workout)
        dialog.window?.setWindowAnimations(R.style.dialog_slide_out)
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window?.setLayout(width, height)

        dialog.dialog_first_workout_button_dismiss.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun checkFirstTimeWorkout() {
        val settings = getSharedPreferences(PREFS_NAME, 0)
        val firstTimeRun = settings.getBoolean("isFirstWorkout", true)
        if (firstTimeRun) {
            showDialog()
        }
        val editor = settings.edit()
        editor.putBoolean("isFirstWorkout", false)
        editor.apply()
    }

}
