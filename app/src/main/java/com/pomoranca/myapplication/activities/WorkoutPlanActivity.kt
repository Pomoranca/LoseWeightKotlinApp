package com.pomoranca.myapplication.activities

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.TransitionDrawable
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.activities.WorkoutActivity.Companion.finalWorkoutList
import com.pomoranca.myapplication.adapters.ItemRecyclerViewAdapter
import com.pomoranca.myapplication.adapters.PlanRecyclerViewAdapter
import com.pomoranca.myapplication.data.Workout
import com.pomoranca.myapplication.data.WorkoutPlan
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_workout_plan.*
import kotlinx.android.synthetic.main.dialog_how_to_workout.*
import kotlinx.android.synthetic.main.dialog_workout_preview.*


class WorkoutPlanActivity : AppCompatActivity() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel
    private val PREFS_NAME = "MyPrefsFile"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_plan)
        val planTitle = intent.getStringExtra("NAME")
        setSupportActionBar(toolbar_plan)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Glide.with(this).load(R.drawable.appbar_background2).centerCrop().into(header_image_source)


        workoutRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        workoutRecyclerView.setHasFixedSize(true)
        val adapter = ItemRecyclerViewAdapter()
        workoutRecyclerView.adapter = adapter

        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        //POPULATE recyclerview regarding which plan is clicked
        when (planTitle) {
            "Beginner workout" -> {
                loseWeightViewModel.getBeginnerWorkouts().observe(this,
                    Observer<List<Workout>> {
                        adapter.workoutList = it as MutableList<Workout>
                        finalWorkoutList = it
                        adapter.notifyDataSetChanged()

                    })
                setValues("40", "20", "3-5")
            }
            "Intermediate workout" -> {
                loseWeightViewModel.getIntermediateWorkouts().observe(this,
                    Observer<List<Workout>> {
                        adapter.workoutList = it as MutableList<Workout>
                        finalWorkoutList = it
                        adapter.notifyDataSetChanged()
                    })
                setValues("45", "15", "3 - 5")

            }
            "Advanced workout" -> {
                loseWeightViewModel.getAdvancedWorkouts().observe(this,
                    Observer<List<Workout>> {
                        adapter.workoutList = it as MutableList<Workout>
                        finalWorkoutList = it
                        adapter.notifyDataSetChanged()
                    })
                setValues("45", "15", "4 - 6")

            }
            "Insane workout" -> {
                loseWeightViewModel.getInsaneWorkouts().observe(this,
                    Observer<List<Workout>> {
                        adapter.workoutList = it as MutableList<Workout>
                        finalWorkoutList = it
                        adapter.notifyDataSetChanged()
                    })
                setValues("50", "10", "5 - 7")

            }
        }
        checkFirstTimeWorkout()

        adapter.setOnItemClickListener(object : ItemRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(workout: Workout) {
                val dialog = Dialog(this@WorkoutPlanActivity, R.style.Theme_Dialog)
                val a = R.layout.dialog_workout_preview
                dialog.setContentView(a)
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.window?.setWindowAnimations(R.style.dialog_fade)
                val title = dialog.findViewById<TextView>(R.id.workout_preview_title)
                title.text = workout.name
                val video = dialog.findViewById<VideoView>(R.id.workout_preview_video)
                val path = "android.resource://" + packageName + "/" + workout.imagePath
                video.setZOrderOnTop(true)
                video.setVideoPath(path)
                video.requestFocus()
                video.start()
                video.setOnPreparedListener {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        val myPlayBackParams = PlaybackParams()
                        myPlayBackParams.speed = 0.7f
                        it.playbackParams = myPlayBackParams
                    }
                    it.isLooping = true

                }
                dialog.setOnDismissListener {
                    video.stopPlayback()
                }

                dialog.show()
            }


        })

        button_begin_workout.setOnClickListener {
            val intent = Intent(this, WorkoutActivity::class.java)
            intent.putExtra("PLAN_TITLE", planTitle)
            startActivity(intent)
        }
    }

    private fun showDialog() {
        val dialog = Dialog(this, R.style.Theme_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.dialog_how_to_workout)
        dialog.window?.setWindowAnimations(R.style.dialog_slide_out)
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

    private fun setValues(work: String, rest: String, sets: String) {
        plan_text_work.text = "Work out for $work seconds"
        plan_text_rest.text = "Rest $rest seconds"
        plan_text_sets.text = "Do $sets sets"
    }


}
