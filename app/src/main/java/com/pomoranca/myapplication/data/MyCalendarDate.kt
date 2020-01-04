package com.pomoranca.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "date_table")
class MyCalendarDate(
    var calendarDate: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString(): String {
        return "${this.calendarDate}"
    }
}