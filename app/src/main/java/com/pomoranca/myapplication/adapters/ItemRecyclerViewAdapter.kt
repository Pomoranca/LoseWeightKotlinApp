package com.pomoranca.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.data.Workout
import kotlinx.android.synthetic.main.workout_recycler_view_item.view.*


class ItemRecyclerViewAdapter : RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemHolder>() {
    var workoutList = mutableListOf<Workout>()


    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerWorkoutImage = itemView.recycler_workout_image!!
        val recyclerWorkoutName = itemView.recycler_workout_name!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.workout_recycler_view_item, parent, false)
        return ItemHolder(itemView)
    }


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val currentWorkout = workoutList[position]

        Glide
            .with(holder.recyclerWorkoutImage.context)
            .load(currentWorkout.imagePath)
            .centerCrop()
            .into(holder.recyclerWorkoutImage)
        holder.recyclerWorkoutName.text = currentWorkout.name
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }


}
