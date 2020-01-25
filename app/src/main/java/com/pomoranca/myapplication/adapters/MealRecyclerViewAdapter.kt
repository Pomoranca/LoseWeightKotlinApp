package com.pomoranca.myapplication.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.activities.TipActivity
import com.pomoranca.myapplication.data.Meal


class MealRecyclerViewAdapter : RecyclerView.Adapter<MealRecyclerViewAdapter.MealHolder>() {

    private lateinit var listener: OnItemClickListener


    var mealList = mutableListOf<Meal>()

    inner class MealHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(mealList[position])
                }
            }
        }

        val mealTitle: TextView = itemView.findViewById(R.id.meal_title)
        val mealBackground: ImageView = itemView.findViewById(R.id.meal_background)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_meal, parent, false)
        return MealHolder(itemView)

    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MealHolder, position: Int) {
        val currentMeal = mealList[position]
        holder.mealTitle.text = currentMeal.title
        holder.mealBackground.setColorFilter(Color.parseColor("#E8D8D8D8"), PorterDuff.Mode.MULTIPLY)

        Glide
            .with(holder.mealBackground.context)
            .load(currentMeal.backgroundPath)
            .centerCrop()
            .into(holder.mealBackground)


        holder.mealBackground.setOnClickListener {
            val intent = Intent(it.context, TipActivity::class.java)
                    intent.putExtra("NAME", currentMeal.title)
                    intent.putExtra("BACKGROUND", currentMeal.backgroundPath)
                    intent.putExtra("DESCRIPTION", currentMeal.description)
                    startActivity(it.context, intent, null)
        }

        }

        fun populateMealList() {
            mealList.add(
                Meal(
                    "Drink more water",
                    "Drinking has it's benefits",
                    R.drawable.tips_water
                )
            )
            mealList.add(
                Meal(
                    "Change habits",
                    "Drinking has it's benefits",
                    R.drawable.tips_change
                )
            )
            mealList.add(
                Meal(
                    "Get enough sleep",
                    "Drinking has it's benefits",
                    R.drawable.tips_sleep
                )
            )
            mealList.add(
                Meal(
                    "Reduce stress",
                    "Drinking has it's benefits",
                    R.drawable.tips_stress
                )
            )
            mealList.add(
                Meal(
                    "Get a routine",
                    "Drinking has it's benefits",
                    R.drawable.tips_training_routine
                )
            )
            mealList.add(
                Meal(
                    "Track your progress",
                    "Drinking has it's benefits",
                    R.drawable.tips_track
                )
            )
            mealList.add(Meal("Eat fibers", "Drinking has it's benefits", R.drawable.tips_fiber))
            mealList.add(
                Meal(
                    "Reduce fat intake",
                    "Drinking has it's benefits",
                    R.drawable.tips_fats
                )
            )
            mealList.add(
                Meal(
                    "Enjoy the process",
                    "Drinking has it's benefits",
                    R.drawable.tips_cheat
                )
            )
            mealList.add(
                Meal(
                    "Avoid alcohol",
                    "Drinking has it's benefits",
                    R.drawable.tips_alcohol
                )
            )
            mealList.add(
                Meal(
                    "Eat proteins",
                    "Drinking has it's benefits",
                    R.drawable.tips_protein
                )
            )
            mealList.add(
                Meal(
                    "Replace juice",
                    "Drinking has it's benefits",
                    R.drawable.tips_replace_juice
                )
            )
            mealList.add(
                Meal(
                    "Avoid fast food",
                    "Drinking has it's benefits",
                    R.drawable.tips_sugary
                )
            )

        }

        interface OnItemClickListener {
            fun onItemClick(meal: Meal)
        }

        fun setOnItemClickListener(listener: OnItemClickListener) {
            this.listener = listener
        }

    }

