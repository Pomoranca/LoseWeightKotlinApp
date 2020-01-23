package com.pomoranca.myapplication.adapters

import android.app.ActionBar
import android.content.SharedPreferences
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.data.LoginQuestion
import kotlinx.android.synthetic.main.fragment_login_two.view.*
import kotlinx.android.synthetic.main.login_recycler_row.view.*
import kotlin.coroutines.coroutineContext

class LoginQuestionRecyclerViewAdapter :
    RecyclerView.Adapter<LoginQuestionRecyclerViewAdapter.LoginQuestionViewHolder>() {

    var questionList = mutableListOf<LoginQuestion>()
    var a = 0
    private val PREFS_NAME = "MyPrefsFile"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoginQuestionViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.login_recycler_row, parent, false)

        return LoginQuestionViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return questionList.size

    }

    override fun onBindViewHolder(holder: LoginQuestionViewHolder, position: Int) {
        val currentQuestion = questionList[position]

        holder.recyclerQuestionTitle.text = currentQuestion.title
        holder.recyclerQuestionImage.setImageResource(currentQuestion.imagePath)
        holder.recyclerQuestionRadio1.text = currentQuestion.questionsList[0]
        holder.recyclerQuestionRadio2.text = currentQuestion.questionsList[1]
        holder.recyclerQuestionRadio3.text = currentQuestion.questionsList[2]
        holder.recyclerQuestionRadio4.text = currentQuestion.questionsList[3]

        val isExpanded = currentQuestion.expanded
        val isFinalQuestion = currentQuestion.finalQuestion
        holder.expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.recyclerQuestionButtonNext.visibility = if(isFinalQuestion) View.GONE else View.VISIBLE
        holder.radioGroupQuestion.visibility =  if(isFinalQuestion) View.GONE else View.VISIBLE

    }

    inner class LoginQuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recyclerQuestionheader: LinearLayout = itemView.login_two_expandable_header
        val recyclerQuestionTitle: TextView = itemView.login_two_expandable_header_title
        val recyclerQuestionImage: ImageView = itemView.login_two_expandable_image
        val recyclerQuestionRadio1: RadioButton = itemView.login_radio1
        val recyclerQuestionRadio2: RadioButton = itemView.login_radio2
        val recyclerQuestionRadio3: RadioButton = itemView.login_radio3
        val recyclerQuestionRadio4: RadioButton = itemView.login_radio4
         val recyclerQuestionButtonNext: Button = itemView.login_two_continue
        val radioGroupQuestion : RadioGroup = itemView.radioGroupFirst


        val expandableLayout: ConstraintLayout = itemView.login_expandable_layout

        init {
            recyclerQuestionButtonNext.setOnClickListener {
                checkButton()
                TransitionManager.beginDelayedTransition(expandableLayout, object: AutoTransition(){})
                val question = questionList[adapterPosition]
                val nextQuestion = questionList[adapterPosition + 1]
                question.expanded = !question.expanded
                nextQuestion.expanded = !question.expanded
                notifyDataSetChanged()
            }
        }
        private fun checkButton() {
            val settings: SharedPreferences = recyclerQuestionButtonNext.context.getSharedPreferences(PREFS_NAME, 0)
            val editor = settings.edit()
            val radioId = radioGroupQuestion.checkedRadioButtonId
            val radioButtonAnswer = itemView.findViewById<RadioButton>(radioId)
            val answer = radioButtonAnswer.text.toString()
            editor.putString("Q $adapterPosition", answer)
            // Commit the edits!
            editor.apply()
        }
    }

    fun populateQuestionList() {
        questionList.add(
            LoginQuestion(
                "What is your current fitness level?",
                R.drawable.login_women_workout,
                arrayListOf("Beginner", "Intermediate", "Advanced", "Insane"), true
            )
        )
        questionList.add(
            LoginQuestion(
                "How often do you workout?",
                R.drawable.login_women_workout,
                arrayListOf("Never", "Rarely", "3-5 times a week", "Every day")
            )
        )
        questionList.add(
            LoginQuestion(
                "All set, we're good to go!",
                R.drawable.welcome_dialog_background,
                arrayListOf("Lorem", "Ipsums", "Data", "Nontos"),
                finalQuestion = true
            )
        )

    }

    fun expand(v: View) {
        val matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(
            (v.parent as View).width,
            View.MeasureSpec.EXACTLY
        )
        val wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(
            0,
            View.MeasureSpec.UNSPECIFIED
        )
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
        val targetHeight = v.measuredHeight
        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.layoutParams.height = 1
        v.visibility = View.VISIBLE
        val a: Animation = object : Animation() {
            override fun applyTransformation(
                interpolatedTime: Float,
                t: Transformation?
            ) {
                v.layoutParams.height =
                    if (interpolatedTime == 1f) ActionBar.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        // Expansion speed of 1dp/ms
        a.duration = ((targetHeight / v.context.resources.displayMetrics.density).toLong())
        v.startAnimation(a)
    }

    fun collapse(v: View) {
        val initialHeight = v.measuredHeight
        val a: Animation = object : Animation() {
            override fun applyTransformation(
                interpolatedTime: Float,
                t: Transformation?
            ) {
                if (interpolatedTime == 1f) {
                    v.visibility = View.GONE
                } else {
                    v.layoutParams.height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        // Collapse speed of 1dp/ms
        a.duration = ((initialHeight / v.context.resources.displayMetrics.density).toLong())
        v.startAnimation(a)
    }

}