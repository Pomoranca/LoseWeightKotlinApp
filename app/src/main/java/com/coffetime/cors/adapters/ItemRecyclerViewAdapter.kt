package com.coffetime.cors.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coffetime.cors.R
import com.coffetime.cors.data.Workout
import kotlinx.android.synthetic.main.workout_recycler_view_item.view.*


class ItemRecyclerViewAdapter: RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemHolder >() {
    var workoutList = mutableListOf<Workout>()
    lateinit var listener: OnItemClickListener


    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(workoutList[position])

                }
            }

        }

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
            .into(holder.recyclerWorkoutImage)
        holder.recyclerWorkoutName.text = currentWorkout.name



    }

    override fun getItemCount(): Int {
        return workoutList.size
    }

    interface OnItemClickListener {
        fun onItemClick(workout: Workout)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


}
