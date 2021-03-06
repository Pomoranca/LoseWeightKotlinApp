package com.coffetime.cors.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_table")
class Workout(
    var name: String,
    var imagePath: Int,
    var difficulty: Int,
    var tipOne : String,
    var tipTwo : String
    ) {

    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}