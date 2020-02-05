package com.pomoranca.myapplication.activities

import android.app.Dialog
import android.content.Intent
import android.media.PlaybackParams
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.activities.WorkoutActivity.Companion.finalWorkoutList
import com.pomoranca.myapplication.adapters.ItemRecyclerViewAdapter
import com.pomoranca.myapplication.data.Workout
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.activity_workout_plan.*


class WorkoutPlanActivity : AppCompatActivity() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel
    private val PREFS_NAME = "MyPrefsFile"


    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
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
            "Beginner" -> {
                loseWeightViewModel.getBeginnerWorkouts().observe(this,
                    Observer<List<Workout>> {
                        adapter.workoutList = it as MutableList<Workout>
                        finalWorkoutList = it
                        adapter.notifyDataSetChanged()

                    })
                setValues("40", "20", "3-5")
            }
            "Intermediate" -> {
                loseWeightViewModel.getIntermediateWorkouts().observe(this,
                    Observer<List<Workout>> {
                        adapter.workoutList = it as MutableList<Workout>
                        finalWorkoutList = it
                        adapter.notifyDataSetChanged()
                    })
                setValues("45", "15", "3 - 5")

            }
            "Advanced" -> {
                loseWeightViewModel.getAdvancedWorkouts().observe(this,
                    Observer<List<Workout>> {
                        adapter.workoutList = it as MutableList<Workout>
                        finalWorkoutList = it
                        adapter.notifyDataSetChanged()
                    })
                setValues("45", "15", "4 - 6")

            }
            "Insane" -> {
                loseWeightViewModel.getInsaneWorkouts().observe(this,
                    Observer<List<Workout>> {
                        adapter.workoutList = it as MutableList<Workout>
                        finalWorkoutList = it
                        adapter.notifyDataSetChanged()
                    })
                setValues("50", "10", "5 - 7")

            }
        }
//        checkFirstTimeWorkout()

        adapter.setOnItemClickListener(object : ItemRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(workout: Workout) {
                val dialog = Dialog(this@WorkoutPlanActivity, R.style.VideoDialog)
                val a = R.layout.dialog_workout_preview
                dialog.setContentView(a)
                dialog.window?.setWindowAnimations(R.style.dialog_fade)
                //dim behind dialog
                val lp = dialog.window!!.attributes
                lp.dimAmount = 0.9f
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



    private fun setValues(work: String, rest: String, sets: String) {
        plan_text_work.text = "Work out for $work seconds"
        plan_text_rest.text = "Rest $rest seconds"
        plan_text_sets.text = "Do $sets sets"
    }


}
