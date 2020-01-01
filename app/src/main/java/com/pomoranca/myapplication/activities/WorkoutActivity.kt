package com.pomoranca.myapplication.activities

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.data.User
import com.pomoranca.myapplication.data.Workout
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.activity_workout.*
import java.util.*

class WorkoutActivity : AppCompatActivity() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel


    private var START_TIME_IN_MILLIS = 3000L
    private var workoutList: List<Workout> = listOf()
    var planTitle: String = ""
    private lateinit var mTextToSpeech: TextToSpeech

    //USER PREFERENCES
    private var userID: Int = 0
    private var userName: String = ""
    private var userMedals = 0
    private var userExperience = 0
    private var userDays = 0

    //updated values
    private var gainedExperience = 0
    private var gainedDays = 0
    private var gainedMedals = 0

    //total values
    private var totalExperience = 0
    private var totalDays = 0
    private var totalMedals = 0

    //medals
    private var hasMedal : Boolean = false
    private var starterMedal : Boolean = false
    private var achieverMedal: Boolean = false
    private var beastMedal : Boolean = false
    private var finisherMedal : Boolean = false
    private var bestOfAllMedal: Boolean = false


    private lateinit var mCountDownTimer: CountDownTimer
    private var mTimerRunning: Boolean = false
    private var mTimeLeftMillis = START_TIME_IN_MILLIS
    private var mEndTime: Long = 0
    private var multiplyFactor = 1
    private var numberOfSets = 0
    var currentSet = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)
        planTitle = intent.getStringExtra("PLAN_TITLE")!!
        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        loseWeightViewModel.getAllUsers().observe(this, Observer<List<User>> {
            userID = it[0].id
            userName = it[0].name
            userMedals = it[0].medals
            userDays = it[0].days
            userExperience = it[0].experience
            hasMedal = it[0].hasMedal
            Log.i("USERNAME------", userName)
            Log.i("EXPERIENCE----", "$userExperience")
        })

        mTextToSpeech = TextToSpeech(this,
            TextToSpeech.OnInitListener { mTextToSpeech.language = Locale.ENGLISH })

        button_start_pause.setOnClickListener {
            if (mTimerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }
//        updateCountDownText()

        populateList()

    }

    private fun startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftMillis
        mCountDownTimer = object : CountDownTimer(mTimeLeftMillis, 100) {
            override fun onFinish() {
                if (currentSet < numberOfSets) {
                    resetTimer()
                    updateCountDownText()
                    mTimerRunning = true
                    this.start()
                    Toast.makeText(
                        this@WorkoutActivity,
                        "Finished workout $currentSet of $numberOfSets",
                        Toast.LENGTH_SHORT
                    ).show()
                    currentSet++
                    changeAnimation()
                } else if (currentSet == numberOfSets) {
                    mTimerRunning = false
                    button_start_pause.text = "Start"
                    resetTimer()
                    mTextToSpeech.speak(
                        "Great job",
                        TextToSpeech.QUEUE_FLUSH,
                        null
                    )
                    gainedExperience = currentSet * multiplyFactor
                    totalExperience =  userExperience + gainedExperience
                    Log.i("GAINED EXPERIENCE!!!", "$gainedExperience")
                    Log.i("GTOTAL EXPERIENCE!!!", "$totalExperience")
                    updateUser()
                    showDialog()

                    currentSet = 1
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftMillis = millisUntilFinished
                updateCountDownText()
            }

        }.start()

        mTimerRunning = true
        button_start_pause.text = "Pause"
    }

    private fun pauseTimer() {
        mCountDownTimer.cancel()
        mTimerRunning = false
        button_start_pause.text = "Start"
    }

    fun updateCountDownText() {
        val seconds = (mTimeLeftMillis / 1000 % 60).toInt()
        text_view_countdown.text = "$seconds"
    }

    //WHEN TIMER REACHES 0
    fun resetTimer() {
        mTimeLeftMillis = START_TIME_IN_MILLIS
        updateButton()
    }

    private fun updateButton() {
        if (mTimerRunning) {
            button_start_pause.text = "Pause"
        } else {
            button_start_pause.text = "Start"
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putLong("milisLeft", mTimeLeftMillis)
        outState.putBoolean("mTimerRunning", mTimerRunning)
        outState.putLong("endTime", mEndTime)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mTimeLeftMillis = savedInstanceState.getLong("milisLeft")
        mTimerRunning = savedInstanceState.getBoolean("mTimerRunning")
        updateCountDownText()
        updateButton()

        if (mTimerRunning) {
            mEndTime = savedInstanceState.getLong("endTime")
            mTimeLeftMillis = mEndTime - System.currentTimeMillis()
            startTimer()
        }
    }

    //PLAY SOUND ON LAST 3 SECONDS of PLAY and REST
    fun playsound() {
    }

    private fun populateList() {
        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        when (planTitle) {
            "Beginner plan" -> loseWeightViewModel.getBeginnerWorkouts().observe(this,
                Observer<List<Workout>> {
                    multiplyFactor = 1
                    numberOfSets = it.size
                    workoutList = it
                    changeAnimation()
                })
            "Intermediate plan" -> loseWeightViewModel.getIntermediateWorkouts().observe(this,
                Observer<List<Workout>> {
                    multiplyFactor = 2
                    numberOfSets = it.size
                    workoutList = it
                    changeAnimation()
                })
            "Advanced plan" -> loseWeightViewModel.getAdvancedWorkouts().observe(this,
                Observer<List<Workout>> {
                    multiplyFactor = 3
                    numberOfSets = it.size
                    workoutList = it
                    changeAnimation()
                })
            "Premium plan" -> loseWeightViewModel.getAllWorkouts().observe(this,
                Observer<List<Workout>> {
                    multiplyFactor = 4
                    numberOfSets = it.size
                    workoutList = it
                    changeAnimation()
                })
        }
    }

    fun changeAnimation() {
        image_current_workout.setImageResource(workoutList[currentSet - 1].imagePath)
        val currentWorkoutText = workoutList[currentSet - 1].name
        text_current_workout.text = "Current workout: ${workoutList[currentSet - 1].name}"
        mTextToSpeech.setSpeechRate(0.9f)
        mTextToSpeech.speak(currentWorkoutText, TextToSpeech.QUEUE_FLUSH, null)
    }

    private fun updateUser() {
        loseWeightViewModel.getAllUsers().observe(this, Observer<List<User>> {
            
        })
        val id = userID
        val updateUser = User(userName,totalDays,totalMedals,totalExperience,hasMedal)
        updateUser.id = id
        loseWeightViewModel.update(updateUser)
    }

    fun getDaysAgo(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)

        return calendar.time
    }

    fun getMedals(){
        when(totalExperience > 1 && !hasMedal) {
        }
    }
    private fun showDialog() {
        val dialog = Dialog(this)
        dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog .setCancelable(false)
        dialog .setContentView(R.layout.dialog_workout_finished)
        dialog .show()
    }
}