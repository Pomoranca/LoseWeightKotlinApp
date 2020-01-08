package com.pomoranca.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.data.WorkoutPlan
import kotlinx.android.synthetic.main.recycler_view_plan.view.*


class PlanRecyclerViewAdapter :
    RecyclerView.Adapter<PlanRecyclerViewAdapter.PlanHolder>() {


    private lateinit var listener: OnItemClickListener

        var planList = mutableListOf<WorkoutPlan>()



    inner class PlanHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(planList[position])
                }
            }
        }

        val courseLength: TextView = itemView.course_length_text
        val planTextView: TextView = itemView.plan_textView
        val daysTextView: TextView = itemView.course_days_text
        val planCard: ImageView = itemView.plan_card_background
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_plan, parent, false)
        return PlanHolder(itemView)
    }

    override fun getItemCount(): Int {
        return planList.size
    }

    override fun onBindViewHolder(holder: PlanHolder, position: Int) {
        val currentPlan = planList[position]
        holder.courseLength.text = "Course length: "
        holder.planTextView.text = currentPlan.name
        holder.daysTextView.text = "${currentPlan.duration} days"
        Glide
            .with(holder.planCard.context)
            .load(currentPlan.backgroundPath)
            .centerCrop()
            .into(holder.planCard)
    }

    fun populatePlanList() {
        planList.add(WorkoutPlan("Beginner plan", 20, R.drawable.plan_beginner, 30, 20))
        planList.add(WorkoutPlan("Intermediate plan", 30, R.drawable.plan_intermediate, 40, 20))
        planList.add(WorkoutPlan("Advanced plan", 40, R.drawable.plan_advanced, 45, 15))
        planList.add(WorkoutPlan("Premium plan", 60, R.drawable.plan_premium, 45, 15))
    }

    interface OnItemClickListener {
        fun onItemClick(workoutPlan: WorkoutPlan)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}