package com.pomoranca.myapplication.activities.fragments


import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.adapters.PlanRecyclerViewAdapter
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment(), SensorEventListener {
    private lateinit var loseWeightViewModel: LoseWeightViewModel
    private var userExperience = 0
    private var medalsWon = 0
    var running = false
    var sensorManager: SensorManager? = null
    lateinit var stepCounterText: TextView
    lateinit var progressBar: ProgressBar


    //SHARED PREFERENCES
    private val PREFS_NAME = "MyPrefsFile"
    var LAST_DATE = ""
    var CURRENT_DATE = ""
    var stepsMadeToday = 0F
    var stepsMadeTotal = 0F

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_main, container, false)


        stepCounterText = rootView.findViewById(R.id.fragment_main_text_step_counter)
        progressBar = rootView.findViewById(R.id.fragment_main_progress_step)
        // Inflate the layout for this fragment
        CURRENT_DATE = getDate()
        getValues()

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
            text_hi.text = "Hi ${it[0].name}"
        })

        val recyclerView = rootView!!.recyclerViewMain
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        val planRecyclerViewAdapter = PlanRecyclerViewAdapter()
        planRecyclerViewAdapter.populatePlanList()
        recyclerView.adapter = planRecyclerViewAdapter


        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        return rootView
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        if (stepSensor == null) {
            fragment_main_text_step_counter.visibility = View.GONE
            fragment_main_progress_step.visibility = View.GONE

        } else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
//        sensorManager?.unregisterListener(this)
        saveValues()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val sensor: Sensor = event?.sensor!!
        val values = event.values
        var value = -1

        if (values.isNotEmpty()) {
            value = values[0].toInt()
        }

        if (sensor.type == Sensor.TYPE_STEP_DETECTOR) {
            stepsMadeToday++
//        stepsMade = event!!.values[0]
            stepCounterText.text = "${stepsMadeToday.toInt()} / 3000"
            progressBar.progress = value
            Log.i("SENSOR", "${event!!.values[0]}")
        }
    }

    private fun getDate(): String { // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        return formatter.format(calendar.time)
    }

    private fun getValues() {
        val settings: SharedPreferences =
            activity!!.getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode
        val editor = settings.edit()
        stepsMadeToday = settings.getFloat("stepsMadeToday", 0F)
        stepCounterText.text = "${stepsMadeToday.toInt()} / 3000"
        stepsMadeTotal = settings.getFloat("stepsMadeTotal", 0F)
        LAST_DATE = settings.getString("lastDate", "")!!

        if (LAST_DATE != CURRENT_DATE) {
            stepsMadeToday = 0F
            LAST_DATE = CURRENT_DATE
            editor.putString("lastDate", LAST_DATE)
            Log.i("steps", "DATES ARE NOT SAME")

        }
        stepCounterText.text = "${stepsMadeToday.toInt()} / 3000"
        progressBar.progress = stepsMadeToday.toInt()
        editor.apply()
    }

    private fun saveValues() {
        val settings: SharedPreferences =
            activity!!.getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode
        val editor = settings.edit()
        stepsMadeTotal = settings.getFloat("stepsMadeTotal", 0F)
        stepsMadeTotal += stepsMadeToday
        Log.i("steps", "TODAY - $stepsMadeToday")
        Log.i("steps", "TOTAL - $stepsMadeTotal")
        Log.i("steps", "DATE - $CURRENT_DATE , $LAST_DATE")



        editor.putFloat("stepsMadeTotal", stepsMadeTotal)
        editor.putFloat("stepsMadeToday", stepsMadeToday)
        editor.apply()

    }


}

