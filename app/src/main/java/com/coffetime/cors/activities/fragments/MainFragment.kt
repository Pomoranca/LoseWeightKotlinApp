package com.coffetime.cors.activities.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coffetime.cors.R
import com.coffetime.cors.adapters.PlanRecyclerViewAdapter
import com.coffetime.cors.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.fragment_main.view.*


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_main, container, false)

        val recyclerView = rootView!!.recyclerViewMain
        recyclerView.layoutManager =
            LinearLayoutManager(rootView.context, RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        val planRecyclerViewAdapter = PlanRecyclerViewAdapter()
        planRecyclerViewAdapter.populatePlanList()
        recyclerView.adapter = planRecyclerViewAdapter

        return rootView
    }


}

