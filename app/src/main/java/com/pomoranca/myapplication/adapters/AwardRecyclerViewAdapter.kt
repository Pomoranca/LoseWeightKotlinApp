package com.pomoranca.myapplication.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.data.Award
import kotlinx.android.synthetic.main.recycler_view_award.view.*

class AwardRecyclerViewAdapter :
    RecyclerView.Adapter<AwardRecyclerViewAdapter.AchievementHolder>() {

    lateinit var listener: OnItemClickListener

    companion object {
        var awardsList = mutableListOf<Award>()
    }

    inner class AchievementHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: ImageView = itemView.award_card_background

        init {
            itemView.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(awardsList[position])

            }
            }

        }
        val background: ImageView = itemView.award_card_background
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_award, parent, false)


        return AchievementHolder(itemView)

    }

    override fun getItemCount(): Int {
        return awardsList.size
    }

    override fun onBindViewHolder(holder: AchievementHolder, position: Int) {
        val currentAward = awardsList[position]
        val isWon = currentAward.won
        if(isWon) {
          holder.itemView.isClickable = true
            holder.card.alpha = 0.9f
        } else if(!isWon) {
            holder.itemView.setOnClickListener {
           Snackbar.make(holder.itemView, "You have not yet won this award :(", Snackbar.LENGTH_LONG).setTextColor(Color.parseColor("#FFFFFF")).show()

            }
            holder.card.alpha = 0.2f
        }

        Glide
            .with(holder.background.context)
            .load(currentAward.imagePath)
            .centerCrop()
            .into(holder.background)
    }

    fun populateAwardList() {
        awardsList.add(Award("Starter","One of the best awards ever this medal is absolutely brilliand", R.drawable.ico_medal_starter))
        awardsList.add(Award("Achiever","One of the best awards ever this medal is absolutely brilliand", R.drawable.ico_medal_achiever))
        awardsList.add(Award("Beast","One of the best awards ever this medal is absolutely brilliand", R.drawable.ico_medal_finisher))
        awardsList.add(Award("Finisher","One of the best awards ever this medal is absolutely brilliand", R.drawable.ico_medal_beast))
        awardsList.add(Award("Ruler","One of the best awards ever this medal is absolutely brilliand", R.drawable.ico_medal_best))
    }

    interface OnItemClickListener {
        fun onItemClick(award: Award)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


}