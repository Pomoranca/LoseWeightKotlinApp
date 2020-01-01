package com.pomoranca.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.pomoranca.myapplication.data.DatabaseRepository
import com.pomoranca.myapplication.data.User
import com.pomoranca.myapplication.data.Workout

class LoseWeightViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: DatabaseRepository = DatabaseRepository(application)
    private var allWorkouts = repository.getAllWorkouts()
    private var beginnerWorkouts = repository.getBeginnerWorkouts()
    private var intermediateWorkouts = repository.getIntermediateWorkouts()
    private var advancedWorkouts = repository.getAdvancedWorkouts()
    private var allUsers = repository.getAllUsers()


    fun getAllWorkouts(): LiveData<List<Workout>> {
        return allWorkouts
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

    fun getAllUsers(): LiveData<List<User>> {
        return allUsers
    }

    fun update(user: User) {
        repository.update(user)
    }

    fun insert(user: User) {
        repository.insert(user)
    }

}


