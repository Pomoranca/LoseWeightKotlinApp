package com.pomoranca.myapplication.activities.fragments


import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.adapters.AwardRecyclerViewAdapter
import com.pomoranca.myapplication.adapters.PlanRecyclerViewAdapter
import com.pomoranca.myapplication.data.Reminders
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import java.nio.file.FileSystem
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel

    lateinit var awardRecyclerViewAdapter: AwardRecyclerViewAdapter


    //SHARED PREFERENCES


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_main, container, false)


        // Inflate the layout for this fragment

        awardRecyclerViewAdapter = AwardRecyclerViewAdapter()
        awardRecyclerViewAdapter.populateAwardList()

        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        //Populate USER OVERVIEW from DATABASE DATA
        loseWeightViewModel.getAllUsers().observe(this, Observer {
            when (it[0].experience) {
                in 1..99 -> {
                    AwardRecyclerViewAdapter.awardsList[0].won = true
                }
                in 100..200 -> {
                    AwardRecyclerViewAdapter.awardsList[0].won = true
                    AwardRecyclerViewAdapter.awardsList[1].won = true

                }
                in 201..500 -> {
                    AwardRecyclerViewAdapter.awardsList[0].won = true
                    AwardRecyclerViewAdapter.awardsList[1].won = true
                    AwardRecyclerViewAdapter.awardsList[2].won = true

                }
                in 501..800 -> {
                    AwardRecyclerViewAdapter.awardsList[0].won = true
                    AwardRecyclerViewAdapter.awardsList[1].won = true
                    AwardRecyclerViewAdapter.awardsList[2].won = true
                    AwardRecyclerViewAdapter.awardsList[3].won = true

                }
                in 801..1000 -> {
                    AwardRecyclerViewAdapter.awardsList[0].won = true
                    AwardRecyclerViewAdapter.awardsList[1].won = true
                    AwardRecyclerViewAdapter.awardsList[2].won = true
                    AwardRecyclerViewAdapter.awardsList[3].won = true
                    AwardRecyclerViewAdapter.awardsList[4].won = true
                }
                else -> Log.i("USEREXP", " ELSE $")


            }
//            text_hi.text = "Hi, ${it[0].name}"
        })
        val recyclerView = rootView!!.recyclerViewMain
        recyclerView.layoutManager = GridLayoutManager(rootView.context, 2, RecyclerView.VERTICAL, false)
//        recyclerView.setHasFixedSize(true)
        val planRecyclerViewAdapter = PlanRecyclerViewAdapter()
        planRecyclerViewAdapter.populatePlanList()
        recyclerView.adapter = planRecyclerViewAdapter




        return rootView
    }





}

