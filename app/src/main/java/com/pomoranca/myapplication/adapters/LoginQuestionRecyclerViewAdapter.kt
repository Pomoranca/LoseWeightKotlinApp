package com.pomoranca.myapplication.adapters

import android.app.ActionBar
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
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
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.activities.MainActivity
import com.pomoranca.myapplication.activities.fragments.LoginFragmentTwo
import com.pomoranca.myapplication.data.LoginQuestion
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.fragment_login_two.view.*
import kotlinx.android.synthetic.main.login_recycler_row.view.*
import kotlin.coroutines.coroutineContext

class LoginQuestionRecyclerViewAdapter :
    RecyclerView.Adapter<LoginQuestionRecyclerViewAdapter.LoginQuestionViewHolder>() {
    var questionList = mutableListOf<LoginQuestion>()
    private val PREFS_NAME = "MyPrefsFile"
    lateinit var loseWeightViewModel: LoseWeightViewModel

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
        val settings: SharedPreferences = holder.itemView.context!!.getSharedPreferences(PREFS_NAME, 0) // 0 - for private mode
        val editor = settings.edit()

        holder.recyclerQuestionTitle.text = currentQuestion.title
        holder.recyclerQuestionImage.setImageResource(currentQuestion.imagePath)
        holder.recyclerQuestionRadio1.text = currentQuestion.questionsList[0]
        holder.recyclerQuestionRadio2.text = currentQuestion.questionsList[1]
        holder.recyclerQuestionRadio3.text = currentQuestion.questionsList[2]
        holder.recyclerQuestionRadio4.text = currentQuestion.questionsList[3]

        val isExpanded = currentQuestion.expanded
        val isFinalQuestion = currentQuestion.finalQuestion
        holder.expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
        if(isFinalQuestion) {
            holder.recyclerQuestionButtonNext.setOnClickListener {
            val intent = Intent(holder.itemView.context, MainActivity::class.java)
            editor.putBoolean("hasLoggedIn", true)
            // Commit the edits!
            editor.apply()

            startActivity(holder.itemView.context, intent, null)
            }
        }
        holder.radioGroupQuestion.visibility =  if(isFinalQuestion) View.GONE else View.VISIBLE

    }

    inner class LoginQuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerQuestionTitle: TextView = itemView.login_two_expandable_header_title
        val recyclerQuestionImage: ImageView = itemView.login_two_expandable_image
        val recyclerQuestionRadio1: RadioButton = itemView.login_radio1
        val recyclerQuestionRadio2: RadioButton = itemView.login_radio2
        val recyclerQuestionRadio3: RadioButton = itemView.login_radio3
        val recyclerQuestionRadio4: RadioButton = itemView.login_radio4
         val recyclerQuestionButtonNext: Button = itemView.login_two_continue
        val loginQuestionCheck: ImageView = itemView.question_checked
        val radioGroupQuestion : RadioGroup = itemView.radioGroupFirst


        val expandableLayout: ConstraintLayout = itemView.login_expandable_layout

        init {
            recyclerQuestionButtonNext.setOnClickListener {
                if(checkButton()) {
                        val question = questionList[adapterPosition]
                        val nextQuestion = questionList[adapterPosition + 1]
                        question.expanded = !question.expanded
                        nextQuestion.expanded = !question.expanded
                    loginQuestionCheck.setImageResource(R.drawable.ic_check)
                        notifyDataSetChanged()
                    } else {
                    Snackbar.make(
                        itemView,
                        "Please select answer",
                        Snackbar.LENGTH_LONG
                    )
                }
            }
        }
        private fun checkButton() : Boolean {
            val radioId = radioGroupQuestion.checkedRadioButtonId
            if(radioId== -1){
                return false
                } else {
                val settings: SharedPreferences =
                    recyclerQuestionButtonNext.context.getSharedPreferences(PREFS_NAME, 0)
                val editor = settings.edit()
                val radioButtonAnswer = itemView.findViewById<RadioButton>(radioId)
                val answer = radioButtonAnswer.text.toString()
                editor.putString("Q $adapterPosition", answer)
                // Commit the edits!
                editor.apply()
                }
            return true
        }
    }

    fun populateQuestionList() {
        questionList.add(
            LoginQuestion(
                "What is your current fitness level?",
                R.drawable.login_man_workout,
                arrayListOf("Beginner", "Intermediate", "Advanced", "Insane"), true
            )
        )
        questionList.add(
            LoginQuestion(
                "How often do you workout?",
                R.drawable.login_woman_workout,
                arrayListOf("Never", "Rarely", "3-5 times a week", "Every day")
            )
        )
        questionList.add(
            LoginQuestion(
                "All set, we're good to go!",
                R.drawable.welcome_dialog_background,
                arrayListOf("", "", "", ""),
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