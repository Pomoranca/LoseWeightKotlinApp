package com.pomoranca.myapplication.adapters

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.activities.WorkoutPlanActivity
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

        //        val courseLength: TextView = itemView.course_length_text
        val planTextView: TextView = itemView.plan_textView
        //        val daysTextView: TextView = itemView.course_days_text
        val planCardImage: ImageView = itemView.plan_card_background
        val planCard: CardView = itemView.plan_card
        val progressBar: ProgressBar = itemView.progressBar_difficulty
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
//        holder.courseLength.text = "Duration:"
        holder.planTextView.text = currentPlan.name
//        holder.daysTextView.text = "${currentPlan.duration} days"
        holder.planCardImage.setColorFilter(Color.parseColor("#E8D8D8D8"), PorterDuff.Mode.MULTIPLY)
        holder.progressBar.progress = currentPlan.duration / 7

        Glide
            .with(holder.planCardImage.context)
            .load(currentPlan.backgroundPath)
            .thumbnail(0.3f)
            .centerCrop()
            .into(holder.planCardImage)
        holder.planCard.setOnClickListener {
            val intent = Intent(it.context, WorkoutPlanActivity::class.java)
            intent.putExtra("NAME", currentPlan.name)
            intent.putExtra("BACKGROUND", currentPlan.backgroundPath)
//            val scaleAnimation: Animation =
//                AnimationUtils.loadAnimation(holder.planCard.context, R.anim.anim_scale)
//            it.startAnimation(scaleAnimation)
            startActivity(it.context, intent, null)

//}

        }
    }

    fun populatePlanList() {
        planList.add(WorkoutPlan("Beginner", 20, R.drawable.plan_beginner, 30, 20))
        planList.add(WorkoutPlan("Intermediate", 30, R.drawable.plan_intermediate, 40, 20))
        planList.add(WorkoutPlan("Advanced", 40, R.drawable.plan_advanced, 45, 15))
        planList.add(WorkoutPlan("Insane", 60, R.drawable.plan_premium, 45, 15))

    }

    interface OnItemClickListener {
        fun onItemClick(workoutPlan: WorkoutPlan)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    private fun showPreview() {

    }

}