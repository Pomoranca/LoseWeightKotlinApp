package com.pomoranca.myapplication.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import ru.cleverpumpkin.calendar.CalendarDate
import java.lang.Exception

class DatabaseRepository(application: Application) {
    private var userDao: UserDao
    private var workoutDao: WorkoutDao
    private var calendarDateDao: CalendarDateDao
    private var allUsers: LiveData<List<User>>
    private var allWorkouts: LiveData<List<Workout>>
    private var allCalendarDates: LiveData<List<MyCalendarDate>>
    //declare all workout plan lists
    private var beginnerWorkouts: LiveData<List<Workout>>
    private var intermediateWorkouts: LiveData<List<Workout>>
    private var advancedWorkouts: LiveData<List<Workout>>


    init {
        val database: LoseWeightDatabase =
            LoseWeightDatabase.getInstance(application.applicationContext)!!
        userDao = database.userDao()
        workoutDao = database.workoutDao()
        calendarDateDao = database.calendarDateDao()

        allUsers = userDao.getAllUsers()
        allWorkouts = workoutDao.getAllWorkouts()
        allCalendarDates = calendarDateDao.getAllCalendarDates()
        beginnerWorkouts = workoutDao.getBeginnerWorkouts()
        intermediateWorkouts = workoutDao.getIntermediateWorkouts()
        advancedWorkouts = workoutDao.getAdvancedWorkouts()
    }

    fun insert(workout: Workout?) {
        val insertWorkoutAsyncTask = InsertWorkoutAsyncTask(workoutDao).execute(workout)
    }

    fun insert(user: User) {
        val insertUserAsyncTask = InsertUserAsyncTask(userDao).execute(user)
    }

    fun insert(myCalendarDate: MyCalendarDate) {
        val insertWorkoutDateAsyncTask =
            InsertCalendarDateAsyncTask(calendarDateDao).execute(myCalendarDate)
    }

    fun update(user: User) {
        val updateUserAsyncTask = UpdateUserAsyncTask(userDao).execute(user)
    }

    fun getAllUsers(): LiveData<List<User>> {
        return allUsers
    }

    fun getAllWorkouts(): LiveData<List<Workout>> {
        return allWorkouts
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

    companion object {
        private class InsertWorkoutAsyncTask(val workoutDao: WorkoutDao) :
            AsyncTask<Workout, Unit, Unit>() {
            override fun doInBackground(vararg params: Workout?) {
                workoutDao.insert(params[0]!!)
            }
        }

        private class InsertUserAsyncTask(val userDao: UserDao) : AsyncTask<User, Unit, Unit>() {
            override fun doInBackground(vararg params: User?) {
                userDao.insert(params[0]!!)
            }
        }

        private class UpdateUserAsyncTask(val userDao: UserDao) : AsyncTask<User, Unit, Unit>() {
            override fun doInBackground(vararg params: User?) {
                userDao.update(params[0]!!)
            }

        }

        private class InsertCalendarDateAsyncTask(val calendarDateDao: CalendarDateDao) :
            AsyncTask<MyCalendarDate, Unit, Unit>() {
            override fun doInBackground(vararg params: MyCalendarDate?) {
                calendarDateDao.insert(params[0]!!)
            }
        }
    }


}