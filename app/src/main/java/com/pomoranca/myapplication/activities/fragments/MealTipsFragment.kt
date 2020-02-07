package com.pomoranca.myapplication.activities.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.adapters.MealRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_meal_tips.view.*

/**
 * A simple [Fragment] subclass.
 */
class MealTipsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView =  inflater.inflate(R.layout.fragment_meal_tips, container, false)
        val recyclerView = rootView!!.recyclerViewMeal
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val mealRecyclerViewAdapter = MealRecyclerViewAdapter()
        mealRecyclerViewAdapter.populateMealList()
        recyclerView.adapter = mealRecyclerViewAdapter

        return  rootView




    }


}
