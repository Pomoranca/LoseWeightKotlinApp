package com.pomoranca.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CalendarDateDao {

    @Insert
    fun insert(myCalendarDate: MyCalendarDate)

    @Query("SELECT * FROM date_table")
    fun getAllCalendarDates(): LiveData<List<MyCalendarDate>>
}