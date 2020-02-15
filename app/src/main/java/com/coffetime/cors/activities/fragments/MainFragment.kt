package com.coffetime.cors.activities.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coffetime.cors.R
import com.coffetime.cors.adapters.PlanRecyclerViewAdapter
import com.coffetime.cors.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.fragment_main.view.*


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel



    //SHARED PREFERENCES


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_main, container, false)


        // Inflate the layout for this fragment

        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        //Populate USER OVERVIEW from DATABASE DATA

        val recyclerView = rootView!!.recyclerViewMain
        recyclerView.layoutManager =
            GridLayoutManager(rootView.context, 1, RecyclerView.VERTICAL, false)
//        recyclerView.setHasFixedSize(true)
        val planRecyclerViewAdapter = PlanRecyclerViewAdapter()
        planRecyclerViewAdapter.populatePlanList()
        recyclerView.adapter = planRecyclerViewAdapter




        return rootView
    }


}

