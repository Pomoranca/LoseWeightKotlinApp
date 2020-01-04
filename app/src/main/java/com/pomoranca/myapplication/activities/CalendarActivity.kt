package com.pomoranca.myapplication.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.data.MyCalendarDate
import com.pomoranca.myapplication.viewmodels.LoseWeightViewModel
import kotlinx.android.synthetic.main.activity_calendar.*
import ru.cleverpumpkin.calendar.CalendarDate
import ru.cleverpumpkin.calendar.CalendarView


class CalendarActivity : AppCompatActivity() {
    private lateinit var loseWeightViewModel: LoseWeightViewModel
     var preselectedDates = mutableListOf<CalendarDate>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        loseWeightViewModel = ViewModelProviders.of(this).get(LoseWeightViewModel::class.java)
        loseWeightViewModel.getAllCalendarDates().observe(this, Observer<List<MyCalendarDate>> { it ->
           for(i in it.indices) {
               preselectedDates.add(CalendarDate(it[i].calendarDate))
               calendar_workout.setupCalendar(
                   selectionMode = CalendarView.SelectionMode.MULTIPLE,
                   selectedDates = preselectedDates
               )
           }
            Toast.makeText(this, "$preselectedDates", Toast.LENGTH_LONG).show()
        })

    }

}