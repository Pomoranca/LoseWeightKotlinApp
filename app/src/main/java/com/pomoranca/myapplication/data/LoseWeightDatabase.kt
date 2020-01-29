package com.pomoranca.myapplication.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pomoranca.myapplication.R
import ru.cleverpumpkin.calendar.CalendarDate

@Database(entities = [User::class, Workout::class, MyCalendarDate::class], version = 1, exportSchema = false)
abstract class LoseWeightDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun calendarDateDao(): CalendarDateDao

    companion object {
        private var instance: LoseWeightDatabase? = null
        fun getInstance(context: Context): LoseWeightDatabase? {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LoseWeightDatabase::class.java,
                        "my_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance!!).execute()
            }
        }
    }

    class PopulateDbAsyncTask(db: LoseWeightDatabase) : AsyncTask<Unit, Unit, Unit>() {
        private val workoutDao = db.workoutDao()
        override fun doInBackground(vararg params: Unit?) {
            workoutDao.insert(Workout("Back abs", R.raw.abs_back, 1))
            workoutDao.insert(Workout("Lower abs", R.raw.abs_lower_1, 2))
            workoutDao.insert(Workout("Lower abs", R.raw.abs_lower_2, 1))
            workoutDao.insert(Workout("Front bend", R.raw.body_front_bend, 1))
            workoutDao.insert(Workout("Burpees", R.raw.burpees, 1))
            workoutDao.insert(Workout("Half burpee", R.raw.burpees_half, 1))
            workoutDao.insert(Workout("High knees", R.raw.highknees, 1))
            workoutDao.insert(Workout("Jumping jacks", R.raw.jumpingjacks, 2))
            workoutDao.insert(Workout("Alternating lunges", R.raw.lunges_alternating, 2))
            workoutDao.insert(Workout("Chair lunges", R.raw.lunges_chair_back, 2))
            workoutDao.insert(Workout("Jump lunges", R.raw.lunges_jump, 2))
            workoutDao.insert(Workout("Kick lunges", R.raw.lunges_kick, 2))
            workoutDao.insert(Workout("Push ups", R.raw.pushups, 3))
            workoutDao.insert(Workout("Arm lift push ups", R.raw.pushup_armlifting, 3))
            workoutDao.insert(Workout("Buttkicks", R.raw.run_leg_back, 3))
            workoutDao.insert(Workout("Chair squats", R.raw.squat_chair, 3))
            workoutDao.insert(Workout("Jump squats", R.raw.squat_jump, 3))
            workoutDao.insert(Workout("Sumo squat", R.raw.squat_sumo, 3))
            workoutDao.insert(Workout("Sumo lift squats", R.raw.squat_sumo_lift_leg, 1))
            workoutDao.insert(Workout("Weight rotate", R.raw.squat_weight_rotate, 1))
            workoutDao.insert(Workout("Wide squats", R.raw.squat_wide, 1))
            workoutDao.insert(Workout("Chair steps", R.raw.step_on_chair, 3))
            workoutDao.insert(Workout("Front superman", R.raw.superman_front_bend, 1))
            workoutDao.insert(Workout("Alternating touches", R.raw.toe_touch_alternating, 2))
        }
    }
}