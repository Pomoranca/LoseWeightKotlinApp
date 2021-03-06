package com.coffetime.cors.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class User(
    var name: String,
    var days: Int,
    var experience: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}