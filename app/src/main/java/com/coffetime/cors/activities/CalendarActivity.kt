package com.coffetime.cors.activities

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.coffetime.cors.R
import com.coffetime.cors.data.MyCalendarDate
import com.coffetime.cors.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.activity_calendar.*
import ru.cleverpumpkin.calendar.CalendarDate
import ru.cleverpumpkin.calendar.CalendarView


class CalendarActivity : AppCompatActivity() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel
     private var preselectedDates = mutableListOf<CalendarDate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_calendar)
        setSupportActionBar(toolbar_calendar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Calendar"


        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        loseWeightViewModel.getAllCalendarDates().observe(this, Observer<List<MyCalendarDate>> {
           for(i in it.indices) {
               preselectedDates.add(CalendarDate(it[i].calendarDate))
               calendar_workout.setupCalendar(
                   selectionMode = CalendarView.SelectionMode.MULTIPLE,
                   selectedDates = preselectedDates
               )
           }
           if(preselectedDates.isEmpty()) {
               text_calendar_title.text = "No recent activities"

               calendar_workout.visibility = View.INVISIBLE
           }
        })

    }

}
