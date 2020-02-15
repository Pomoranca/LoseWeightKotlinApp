package com.coffetime.cors.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.coffetime.cors.data.DatabaseRepository
import com.coffetime.cors.data.MyCalendarDate
import com.coffetime.cors.data.User
import com.coffetime.cors.data.Workout

class LoseWeightViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: DatabaseRepository = DatabaseRepository(application)
    private var allUsers = repository.getAllUsers()
    private var allCalendarDates = repository.getAllCalendarDates()
    private var beginnerWorkouts = repository.getBeginnerWorkouts()
    private var intermediateWorkouts = repository.getIntermediateWorkouts()
    private var advancedWorkouts = repository.getAdvancedWorkouts()
    private var insaneWorkouts = repository.getInsaneWorkouts()


    fun getInsaneWorkouts(): LiveData<List<Workout>> {
        return insaneWorkouts
    }

    fun getAllUsers(): LiveData<List<User>> {
        return allUsers
    }


    fun getAllCalendarDates(): LiveData<List<MyCalendarDate>> {
        return allCalendarDates
    }

    fun getIntermediateWorkouts(): LiveData<List<Workout>> {
        return intermediateWorkouts
    }

    fun getAdvancedWorkouts(): LiveData<List<Workout>> {
        return advancedWorkouts
    }

    fun getBeginnerWorkouts(): LiveData<List<Workout>> {
        return beginnerWorkouts
    }

    fun update(user: User) {
        repository.update(user)
    }

    fun insert(user: User) {
        repository.insert(user)
    }

    fun insert(myCalendarDate: MyCalendarDate) {
        repository.insert(myCalendarDate)
    }

}


