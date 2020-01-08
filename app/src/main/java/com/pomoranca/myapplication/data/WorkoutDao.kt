package com.pomoranca.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WorkoutDao {

    @Insert
    fun insert(workout: Workout)

    @Query("SELECT * FROM workout_table")
    fun getAllWorkouts(): LiveData<List<Workout>>

    @Query("SELECT * FROM workout_table WHERE difficulty = 1")
    fun getBeginnerWorkouts(): LiveData<List<Workout>>

    @Query("SELECT * FROM workout_table WHERE difficulty = 2")
    fun getIntermediateWorkouts(): LiveData<List<Workout>>

    @Query("SELECT * FROM workout_table WHERE difficulty = 3 ")
    fun getAdvancedWorkouts(): LiveData<List<Workout>>

}