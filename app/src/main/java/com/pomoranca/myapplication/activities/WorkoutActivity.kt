package com.pomoranca.myapplication.activities

import android.app.Dialog
import android.content.Intent
import android.content.res.Configuration
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.DisplayMetrics
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.SharedPref
import com.pomoranca.myapplication.data.MyCalendarDate
import com.pomoranca.myapplication.data.User
import com.pomoranca.myapplication.data.Workout
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.activity_workout.*
import kotlinx.android.synthetic.main.dialog_stop_workout.*
import kotlinx.android.synthetic.main.dialog_workout_finished.*
import java.text.SimpleDateFormat
import java.util.*


class WorkoutActivity : AppCompatActivity() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel
    private lateinit var video: VideoView
    private var stoppedPosition: Int = 100

    private var START_TIME_IN_MILLIS = 40000L
    private var REST_TIME_IN_MILLIS = 15000L

    companion object {
        var finalWorkoutList: List<Workout> = listOf()
    }

    private var planTitle: String = ""
    private lateinit var mTextToSpeech: TextToSpeech


    //SHARED PREFERENCES
    private var LAST_DATE = 0L
    private var LAST_DATE_CONVERTED = ""
    private var CURRENT_DATE = 0L
    private var CURRENT_DATE_CONVERTED = ""

    private lateinit var sharedPref: SharedPref


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
    private var firstDayDone: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_workout)

        //load shared preferences
        sharedPref = SharedPref(this)


        setSupportActionBar(toolbar_workout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        video = findViewById(R.id.image_current_workout)

        //adjust progressbar size according to screen width
        val orientation = resources.configuration.orientation
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //LANDSCAPE SETTINGS  - progress bar takes 1/4 of width
            progress_bar.layoutParams.height = width / 4
            progress_bar.layoutParams.width = width / 4
        } else {
            //PORTRAIT SETTINGS - progress bar takes 1/2 of width
            progress_bar.layoutParams.height = width / 2
            progress_bar.layoutParams.width = width / 2
        }
        //Iniatialize TextToSpeech Engine
        mTextToSpeech = TextToSpeech(
            this,
            TextToSpeech.OnInitListener { mTextToSpeech.language = Locale.ENGLISH }
        )

        if (stoppedPosition == 100) {
            startFirstVideo()
        }
        current_workout_overdraw.text = "GET READY"

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



        button_start_pause.setOnClickListener {
            if (mTimerRunning) {
                pauseTimer()
            } else {
                startTimer()
                startVideoLooped()
            }
        }
        workout_set_number.text = "$currentSet / $numberOfSets"
        progress_bar.progress = (START_TIME_IN_MILLIS / 1000).toInt()
        text_view_countdown.text = (START_TIME_IN_MILLIS / 1000).toString()
        updateText()
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
            resources.getColor(R.color.lightGreen), android.graphics.PorterDuff.Mode.SRC_IN
        )

        mTextToSpeech.setSpeechRate(0.9f)
        speakCurrentWorkout()
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

                }
            }
        }.start()

        mTimerRunning = true
        button_start_pause.text = "Pause"
    }

    private fun pauseTimer() {
        mCountDownTimer.cancel()
        mTimerRunning = false
        video.pause()
        toggleAnimation()

        button_start_pause.text = "Start"
        button_start_pause.visibility = View.VISIBLE
        mTextToSpeech.setSpeechRate(1f)
        speakWorkoutPaused()
        progress_bar.progressDrawable.setColorFilter(
            resources.getColor(R.color.timerPausedD), android.graphics.PorterDuff.Mode.SRC_IN
        )

    }

    fun updateCountDownText() {
        val seconds = ((mTimeLeftMillis / 1000) % 60).toInt()
        text_view_countdown.text = "$seconds"
        progress_bar.progress = seconds

        when (progress_bar.progress) {
            //progressbar at rest time
            (REST_TIME_IN_MILLIS / 1000).toInt() -> {
                if (currentSet != numberOfSets) {
                    speakRest()
                    progress_bar.progressDrawable.setColorFilter(
                        resources.getColor(R.color.colorAccent),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                    toggleButtonVisibility()
                    text_current_workout.text = "Take a rest"
                    startVideoUnLooped()
                    playsound()
                } else {
                    //if last set don't rest, end workout
                    finishWorkout()
                    Log.i("ENDEX", "END WORKOUT HERE")
                }
            }
            (REST_TIME_IN_MILLIS / 1000 - 1).toInt() -> {
                if (currentSet != numberOfSets) {
                    speakNextWorkout()
                }
            }

            (START_TIME_IN_MILLIS / 1000 - 10).toInt() -> {
                speakInformationOne()
            }
            (REST_TIME_IN_MILLIS / 1000 + 10).toInt() -> {
                speakTenSecondsRemaining()
            }
            in REST_TIME_IN_MILLIS / 1000..REST_TIME_IN_MILLIS / 1000 + 5 -> speakLastFiveSeconds()
            3 -> if (currentSet != numberOfSets) speakGetReady()
            ((START_TIME_IN_MILLIS / 1000) - 1).toInt() -> {
                progress_bar.progressDrawable.setColorFilter(
                    resources.getColor(R.color.lightGreen), android.graphics.PorterDuff.Mode.SRC_IN
                )
                playsound()
                text_current_workout.text = finalWorkoutList[currentSet - 1].name
                video.visibility = View.VISIBLE
            }
        }

    }


    //WHEN TIMER REACHES 0
    private fun resetTimer() {
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
        outState.putInt("stoppedPosition", video.currentPosition)
        Log.i("AAA", "SAVED $video.currentPosition")
        outState.putLong("milisLeft", mTimeLeftMillis)
        outState.putBoolean("mTimerRunning", mTimerRunning)
        outState.putLong("endTime", mEndTime)
        outState.putInt("currentSet", currentSet)
        outState.putInt("currentVideoMill", video.currentPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("AAA", "RESTORED $video.currentPosition")
        stoppedPosition = savedInstanceState.getInt("stoppedPosition", 100)
        Log.i("AAA", "STOPPED POSITION $stoppedPosition")
        mTimeLeftMillis = savedInstanceState.getLong("milisLeft")
        mTimerRunning = savedInstanceState.getBoolean("mTimerRunning")
        currentSet = savedInstanceState.getInt("currentSet")
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
            "Beginner" -> {
                loseWeightViewModel.getBeginnerWorkouts().observe(this,
                    Observer<List<Workout>> {
                        multiplyFactor = 1
                        numberOfSets = it.size
                        workout_set_number.text = "$currentSet / $numberOfSets"
                    })
            }
            "Intermediate" -> {
                loseWeightViewModel.getIntermediateWorkouts().observe(this,
                    Observer<List<Workout>> {
                        multiplyFactor = 2
                        numberOfSets = it.size
                        workout_set_number.text = "$currentSet / $numberOfSets"
                    })
            }
            "Advanced" -> {
                loseWeightViewModel.getAdvancedWorkouts().observe(this,
                    Observer<List<Workout>> {
                        multiplyFactor = 3
                        numberOfSets = it.size
                        workout_set_number.text = "$currentSet / $numberOfSets"
                    })
            }
            "Insane" -> {
                loseWeightViewModel.getInsaneWorkouts().observe(this,
                    Observer<List<Workout>> {
                        multiplyFactor = 4
                        numberOfSets = it.size
                        finalWorkoutList = it
                        workout_set_number.text = "$currentSet / $numberOfSets"
                    })
            }
        }
    }

    fun changeAnimation() {
        startVideoLooped()
        updateText()

        mTextToSpeech.setSpeechRate(0.9f)
        speakCurrentWorkout()
        workout_set_number.text = "$currentSet / $numberOfSets"

    }

    private fun showDialogFinish() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "I just finished my workout using Daily. Join us #dailyworkoutapp"
            )

            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share activity").apply {

        }
        val dialog = Dialog(this, R.style.ThemeDialog)
        val lp = dialog.window!!.attributes
        lp.dimAmount = 0.9f
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
        val dialog = Dialog(this, R.style.ThemeDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val lp = dialog.window!!.attributes
        lp.dimAmount = 0.9f
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_stop_workout)
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
            "Beginner" -> {
                REST_TIME_IN_MILLIS = 10000
                START_TIME_IN_MILLIS = 10000 + REST_TIME_IN_MILLIS
                mTimeLeftMillis = START_TIME_IN_MILLIS
            }
            "Intermediate" -> {
                REST_TIME_IN_MILLIS = 15000
                START_TIME_IN_MILLIS = 45000 + REST_TIME_IN_MILLIS
                mTimeLeftMillis = START_TIME_IN_MILLIS
            }
            "Advanced" -> {
                REST_TIME_IN_MILLIS = 15000
                START_TIME_IN_MILLIS = 45000 + REST_TIME_IN_MILLIS
                mTimeLeftMillis = START_TIME_IN_MILLIS
            }
            "Insane" -> {
                REST_TIME_IN_MILLIS = 10000
                START_TIME_IN_MILLIS = 50000 + REST_TIME_IN_MILLIS
                mTimeLeftMillis = START_TIME_IN_MILLIS
            }
        }
    }


    private fun toggleAnimation() = when {
        video.isPlaying -> video.setOnPreparedListener {
            it.pause()
        }
        else -> {
            video.setOnPreparedListener {
                if (mTimerRunning) {
                    it.isLooping = true
                    it.start()
                } else {
                    it.seekTo(stoppedPosition)
                }
            }
        }

    }

    private fun resetAnimation() {
        button_start_pause.visibility = View.VISIBLE
    }

    private fun startVideoLooped() {
        current_workout_overdraw.visibility = View.INVISIBLE
        image_current_workout.visibility = View.VISIBLE
        val path =
            "android.resource://" + packageName + "/" + finalWorkoutList[currentSet - 1].imagePath
        video.setVideoPath(path)
        video.requestFocus()
        video.setOnPreparedListener {
            it.isLooping = true
        }
        video.start()
    }

    private fun startVideoUnLooped() {
        current_workout_overdraw.visibility = View.VISIBLE
        val path =
            "android.resource://" + packageName + "/" + finalWorkoutList[currentSet].imagePath
        video.setVideoPath(path)
        video.setOnPreparedListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val myPlayBackParams = PlaybackParams()
                myPlayBackParams.speed = 0.6f
                it.playbackParams = myPlayBackParams

            }
            it.isLooping = false
        }
        video.setOnCompletionListener {
            if (!mTimerRunning) {
                button_start_pause.visibility = View.VISIBLE
            }

        }
        video.start()
    }

    private fun startFirstVideo() {
        current_workout_overdraw.visibility = View.VISIBLE
        val path =
            "android.resource://" + packageName + "/" + finalWorkoutList[0].imagePath
        video.setVideoPath(path)
        video.setOnPreparedListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val myPlayBackParams = PlaybackParams()
                myPlayBackParams.speed = 0.6f
                it.playbackParams = myPlayBackParams
            }
            it.isLooping = false
        }
        video.setOnCompletionListener {
            if (!mTimerRunning) {
                button_start_pause.visibility = View.VISIBLE
            }

        }
        video.start()
    }


    private fun updateText() {
        text_current_workout.text = finalWorkoutList[currentSet - 1].name
        informationOne.text = finalWorkoutList[currentSet - 1].tipOne
        informationTwo.text = finalWorkoutList[currentSet - 1].tipTwo
    }

    private fun toggleButtonVisibility() {
        if (button_start_pause.visibility == View.VISIBLE) {
            button_start_pause.visibility = View.INVISIBLE
        } else {
            button_start_pause.visibility = View.INVISIBLE
        }


    }

    private fun speakCurrentWorkout() {
        if (sharedPref.loadNarrationState()) {
            mTextToSpeech.speak(
                finalWorkoutList[currentSet - 1].name,
                TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
        }
    }

    private fun speakNextWorkout() {
        if (sharedPref.loadNarrationState()) {
            mTextToSpeech.speak(
                "Next workout," + finalWorkoutList[currentSet].name,
                TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
        }
    }

    private fun speakRest() {
        if (sharedPref.loadNarrationState()) {

            mTextToSpeech.speak(
                "Rest",
                TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
        }
    }

    private fun speakTenSecondsRemaining() {
        if (sharedPref.loadNarrationState()) {

            mTextToSpeech.speak(
                "Ten seconds remaining",
                TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
        }
    }

    private fun speakLastFiveSeconds() {
        if (sharedPref.loadNarrationState()) {

            mTextToSpeech.speak(
                "${progress_bar.progress - REST_TIME_IN_MILLIS / 1000}",
                TextToSpeech.QUEUE_FLUSH,
                null
            )
        }
    }

    private fun speakGreatJob() {
        if (sharedPref.loadNarrationState()) {

            mTextToSpeech.speak(
                "Great job",
                TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
        }
    }

    private fun speakWorkoutPaused() {
        if (sharedPref.loadNarrationState()) {

            mTextToSpeech.speak(
                "Workout paused",
                TextToSpeech.QUEUE_FLUSH,
                null
            )
        }
    }

    private fun speakInformationOne() {
        if (sharedPref.loadNarrationState()) {

            mTextToSpeech.speak("${informationOne.text}", TextToSpeech.QUEUE_FLUSH, null)
        }

    }

    private fun speakGetReady() {
        if (sharedPref.loadNarrationState()) {

            mTextToSpeech.speak("Get ready", TextToSpeech.QUEUE_FLUSH, null)
        }
    }

    private fun finishWorkout() {
        mCountDownTimer.cancel()
        video.stopPlayback()
        button_start_pause.visibility = View.INVISIBLE
        progress_bar.progress = 0
        mTimerRunning = false
        mTimeLeftMillis = 0
        button_start_pause.text = "Start"
        resetTimer()
        speakGreatJob()
        gainedExperience = currentSet * multiplyFactor
        totalExperience = userExperience + gainedExperience
        updateUser()
        showDialogFinish()
        currentSet = 1
    }


}