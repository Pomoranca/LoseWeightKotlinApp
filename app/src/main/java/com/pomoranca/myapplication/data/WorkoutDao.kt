package com.pomoranca.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WorkoutDao {

    @Insert
    fun insert(workout: Workout)

    @Query("SELECT * FROM workout_table WHERE difficulty = 1 ORDER BY RANDOM() LIMIT 5")
    fun getBeginnerWorkouts(): LiveData<List<Workout>>

    @Query("SELECT * FROM workout_table WHERE difficulty = 2 ORDER BY RANDOM() LIMIT 6")
    fun getIntermediateWorkouts(): LiveData<List<Workout>>

    @Query("SELECT * FROM workout_table WHERE difficulty = 3 ORDER BY RANDOM() LIMIT 7 ")
    fun getAdvancedWorkouts(): LiveData<List<Workout>>
    @Query("SELECT * FROM workout_table WHERE difficulty = 3 ORDER BY RANDOM() LIMIT 7")
    fun getInsaneWorkouts(): LiveData<List<Workout>>


}