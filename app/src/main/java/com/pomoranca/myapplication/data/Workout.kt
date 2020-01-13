package com.pomoranca.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_table")
class Workout(
    var name: String,
    var imagePath: Int,
    var difficulty: Int) {

    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}