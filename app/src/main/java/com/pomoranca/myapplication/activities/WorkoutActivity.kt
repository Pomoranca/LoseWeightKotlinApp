package com.pomoranca.myapplication.activities

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.DisplayMetrics
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.data.MyCalendarDate
import com.pomoranca.myapplication.data.User
import com.pomoranca.myapplication.data.Workout
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.activity_workout.*
import kotlinx.android.synthetic.main.dialog_stop_workout.*
import kotlinx.android.synthetic.main.dialog_workout_finished.*
import pl.droidsonroids.gif.AnimationListener
import pl.droidsonroids.gif.GifDrawable
import java.text.SimpleDateFormat
import java.util.*


class WorkoutActivity : AppCompatActivity(), AnimationListener {
    private lateinit var loseWeightViewModel: LoseWeightViewModel


    private var START_TIME_IN_MILLIS = 40000L
    private var REST_TIME_IN_MILLIS = 15000L

    companion object {
        var finalWorkoutList: List<Workout> = listOf()
    }

    var planTitle: String = ""
    private lateinit var mTextToSpeech: TextToSpeech


    //SHARED PREFERENCES
    var LAST_DATE = 0L
    var LAST_DATE_CONVERTED = ""
    var CURRENT_DATE = 0L
    var CURRENT_DATE_CONVERTED = ""
    private lateinit var gifDrawable: GifDrawable


    //USER PREFERENCES
    private var userID: Int = 0
    private var userName: String = ""

    private var userExperience = 0
    //updated values
    private var gainedExperience = 0

    //total values
    private var totalExperience = 0
    private var totalDays = 0


    private lateinit var mCountDownTimer: CountDownTimer
    private var mTimerRunning: Boolean = false
    private var mTimeLeftMillis = START_TIME_IN_MILLIS
    private var mEndTime: Long = 0
    private var multiplyFactor = 1
    private var numberOfSets = 0
    var currentSet = 1
    var firstDayDone: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)
        setSupportActionBar(toolbar_workout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //adjust progressbar size according to screen width
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels / 2
        progress_bar.layoutParams.height = width
        progress_bar.layoutParams.width = width


        val path = finalWorkoutList[currentSet - 1].imagePath
        gifDrawable = GifDrawable.createFromResource(resources, path) as GifDrawable
//        gifDrawable.setSpeed(1.5f)
        gifDrawable.addAnimationListener(this)
        resetAnimation()

        title = "Workout"
        planTitle = intent.getStringExtra("PLAN_TITLE")!!

        prepareTimer()
        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)

        val calendar = Calendar.getInstance()
        CURRENT_DATE = calendar.timeInMillis
        CURRENT_DATE_CONVERTED = getDate(CURRENT_DATE)

        loseWeightViewModel.getAllCalendarDates().observe(this, Observer<List<MyCalendarDate>> {
            if (it.isNotEmpty()) {
                LAST_DATE = it.last().calendarDate
                LAST_DATE_CONVERTED = getDate(LAST_DATE)
                totalDays = it.size
            }
        })

        loseWeightViewModel.getAllUsers().observe(this, Observer<List<User>> {
            userID = it[0].id
            userName = it[0].name
            userExperience = it[0].experience
        })


        mTextToSpeech = TextToSpeech(
            this,
            TextToSpeech.OnInitListener { mTextToSpeech.language = Locale.ENGLISH },
            "com.google.android.tts"
        )

        button_start_pause.setOnClickListener {
            if (mTimerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }
        workout_set_number.text = "$currentSet / $numberOfSets"
        progress_bar.progress = (START_TIME_IN_MILLIS / 1000).toInt()
        text_view_countdown.text = (START_TIME_IN_MILLIS / 1000).toString()
        text_current_workout.text = "${finalWorkoutList[currentSet - 1].name}"
        populateList()

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    private fun updateUser() {
        if (totalDays == 0) {
            loseWeightViewModel.insert(MyCalendarDate(CURRENT_DATE))
            totalDays++
            firstDayDone = true

        } else if (!firstDayDone && totalDays != 0 && LAST_DATE_CONVERTED != CURRENT_DATE_CONVERTED) {
            loseWeightViewModel.insert(MyCalendarDate((CURRENT_DATE)))
            totalDays++
        }

        val id = userID
        val updateUser = User(userName, totalDays, totalExperience)
        updateUser.id = id
        loseWeightViewModel.update(updateUser)
    }

    private fun startTimer() {
        toggleAnimation()
        progress_bar.progressDrawable.setColorFilter(
            resources.getColor(R.color.startTimer), android.graphics.PorterDuff.Mode.SRC_IN
        )

        val currentWorkoutText = finalWorkoutList[currentSet - 1].name
        mTextToSpeech.setSpeechRate(0.9f)
        mTextToSpeech.speak(currentWorkoutText, TextToSpeech.QUEUE_FLUSH, null)
        mCountDownTimer = object : CountDownTimer(mTimeLeftMillis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                if (currentSet < numberOfSets) {
                    updateCountDownText()
                    mTimerRunning = true
                    currentSet++
                    changeAnimation()
                    resetAnimation()
                    mTimeLeftMillis = START_TIME_IN_MILLIS
                    startTimer()
                    Log.i("LAST", "$currentSet")
                    if (currentSet == numberOfSets - 1) {
                        REST_TIME_IN_MILLIS = 0
                    }
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
                    totalExperience = userExperience + gainedExperience
                    updateUser()
                    showDialogFinish()

                    currentSet = 1
                }
            }
        }.start()

        mTimerRunning = true
        button_start_pause.text = "Pause"
    }

    private fun pauseTimer() {
        mCountDownTimer.cancel()
        mTimerRunning = false
        toggleAnimation()

        button_start_pause.text = "Start"
        mTextToSpeech.setSpeechRate(1f)
        mTextToSpeech.speak("Workout paused", TextToSpeech.QUEUE_FLUSH, null)
        progress_bar.progressDrawable.setColorFilter(
            resources.getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN
        )

    }

    fun updateCountDownText() {
        val seconds = ((mTimeLeftMillis / 1000) % 60).toInt()
        text_view_countdown.text = "$seconds"
        progress_bar.progress = seconds

        when (progress_bar.progress) {
            (REST_TIME_IN_MILLIS / 1000).toInt() -> {
                mTextToSpeech.speak("Rest", TextToSpeech.QUEUE_FLUSH, null)
                progress_bar.progressDrawable.setColorFilter(
                    resources.getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN
                )
                button_start_pause.visibility = View.INVISIBLE
                text_current_workout.text = "Take a rest!"
                image_current_workout.visibility = View.INVISIBLE
                playsound()
            }
            (START_TIME_IN_MILLIS / 1000 - 10).toInt() -> {
                mTextToSpeech.speak("${informationOne.text}", TextToSpeech.QUEUE_FLUSH, null)
            }
            (REST_TIME_IN_MILLIS / 1000 + 10).toInt() -> {
                mTextToSpeech.speak("Ten seconds remaining", TextToSpeech.QUEUE_FLUSH, null)
            }
            in REST_TIME_IN_MILLIS / 1000..REST_TIME_IN_MILLIS / 1000 + 5 -> mTextToSpeech.speak(
                "${progress_bar.progress - REST_TIME_IN_MILLIS / 1000}",
                TextToSpeech.QUEUE_FLUSH,
                null
            )
            5 -> mTextToSpeech.speak("Get ready", TextToSpeech.QUEUE_FLUSH, null)
            ((START_TIME_IN_MILLIS / 1000) - 1).toInt() -> {
                progress_bar.progressDrawable.setColorFilter(
                    resources.getColor(R.color.startTimer), android.graphics.PorterDuff.Mode.SRC_IN
                )
                playsound()
                text_current_workout.text = "${finalWorkoutList[currentSet - 1].name}"
                image_current_workout.visibility = View.VISIBLE
            }
        }

    }

    //WHEN TIMER REACHES 0
    fun resetTimer() {
        mTimeLeftMillis = START_TIME_IN_MILLIS
        updateCountDownText()
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
    private fun playsound() {
        val mp: MediaPlayer = MediaPlayer.create(this, R.raw.sound_finished)
        mp.start()
    }

    private fun populateList() {
        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        when (planTitle) {
            "Beginner plan" -> {
                loseWeightViewModel.getBeginnerWorkouts().observe(this,
                    Observer<List<Workout>> {
                        multiplyFactor = 1
                        numberOfSets = it.size
                        changeAnimation()
                    })
            }
            "Intermediate plan" -> {
                loseWeightViewModel.getIntermediateWorkouts().observe(this,
                    Observer<List<Workout>> {
                        multiplyFactor = 2
                        numberOfSets = it.size
                        changeAnimation()
                    })
            }
            "Advanced plan" -> {
                loseWeightViewModel.getAdvancedWorkouts().observe(this,
                    Observer<List<Workout>> {
                        multiplyFactor = 3
                        numberOfSets = it.size
                        changeAnimation()
                    })
            }
            "Insane plan" -> {
                loseWeightViewModel.getInsaneWorkouts().observe(this,
                    Observer<List<Workout>> {
                        multiplyFactor = 4
                        numberOfSets = it.size
                        finalWorkoutList = it
                        changeAnimation()
                    })
            }
        }
    }

    fun changeAnimation() {
//
        image_current_workout.setImageDrawable(gifDrawable)

        val currentWorkoutText = text_current_workout.text.toString()
        text_current_workout.text = currentWorkoutText
        mTextToSpeech.setSpeechRate(0.9f)
        mTextToSpeech.speak(currentWorkoutText, TextToSpeech.QUEUE_FLUSH, null)
        workout_set_number.text = "$currentSet / $numberOfSets"

    }

    private fun showDialogFinish() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "I just finished my workout using OneMinute Workout App. Join us @OneMinuteWorkout"
            )

            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share activity").apply {

        }
        val dialog = Dialog(this, R.style.Theme_Dialog)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setWindowAnimations(R.style.dialog_slide)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_workout_finished)
        if (gainedExperience > 0) {
            dialog.text_report.text =
                "Experience gained: $gainedExperience"
        }
        dialog.dialog_button_end_workout.setOnClickListener {
            this@WorkoutActivity.finish()
        }
        dialog.finish_button_share.setOnClickListener {
            startActivity(shareIntent)
        }
        dialog.show()
    }

    private fun getDate(
        milliSeconds: Long
    ): String { // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    override fun onPause() {
        super.onPause()
        if (mTimerRunning) {
            pauseTimer()
        }
    }

    override fun onBackPressed() {
        mTextToSpeech.shutdown()
        showDialogBack()
    }

    private fun showDialogBack() {
        val dialog = Dialog(this, R.style.Theme_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_stop_workout)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.dialog_button_continue.setOnClickListener {
            dialog.dismiss()
        }
        dialog.dialog_button_leave.setOnClickListener {
            dialog.dismiss()
            this@WorkoutActivity.finish()
        }
        dialog.show()
    }

    private fun prepareTimer() {
        when (planTitle) {
            "Beginner plan" -> {
                REST_TIME_IN_MILLIS = 1000
                START_TIME_IN_MILLIS = 20000 + REST_TIME_IN_MILLIS
                mTimeLeftMillis = START_TIME_IN_MILLIS
            }
            "Intermediate plan" -> {
                REST_TIME_IN_MILLIS = 15000
                START_TIME_IN_MILLIS = 45000 + REST_TIME_IN_MILLIS
                mTimeLeftMillis = START_TIME_IN_MILLIS
            }
            "Advanced plan" -> {
                REST_TIME_IN_MILLIS = 15000
                START_TIME_IN_MILLIS = 45000 + REST_TIME_IN_MILLIS
                mTimeLeftMillis = START_TIME_IN_MILLIS
            }
            "Insane plan" -> {
                REST_TIME_IN_MILLIS = 10000
                START_TIME_IN_MILLIS = 50000 + REST_TIME_IN_MILLIS
                mTimeLeftMillis = START_TIME_IN_MILLIS
            }
        }
    }


    private fun toggleAnimation() = when {
        gifDrawable.isPlaying -> gifDrawable.stop()
        else -> gifDrawable.start()
    }

    override fun onAnimationCompleted(loopNumber: Int) {

    }

    private fun resetAnimation() {
        gifDrawable.stop()
        gifDrawable.loopCount = 4
        gifDrawable.seekToFrameAndGet(5)
        button_start_pause.visibility = View.VISIBLE

    }


}