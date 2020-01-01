package com.pomoranca.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.data.Workout
import kotlinx.android.synthetic.main.workout_recycler_view_item.view.*


class ItemRecyclerViewAdapter : ListAdapter<Workout, ItemRecyclerViewAdapter.ItemHolder>(
    DIFF_CALLBACK
) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Workout> =
            object : DiffUtil.ItemCallback<Workout>() {
                override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
                    return oldItem.name == newItem.name && oldItem.difficulty == newItem.difficulty
                            && oldItem.imagePath == newItem.imagePath
                }

            }
    }

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
        val currentWorkout = getItem(position)
        holder.recyclerWorkoutImage.setImageResource(currentWorkout.imagePath)
        holder.recyclerWorkoutName.text = currentWorkout.name
    }

}
