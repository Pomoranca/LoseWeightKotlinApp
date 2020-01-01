package com.pomoranca.myapplication.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pomoranca.myapplication.R

@Database(entities = [User::class, Workout::class], version = 1, exportSchema = false)
abstract class LoseWeightDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun workoutDao(): WorkoutDao

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
        private val userDao = db.userDao()
        private val workoutDao = db.workoutDao()
        override fun doInBackground(vararg params: Unit?) {
            workoutDao.insert(Workout("Back abs", R.drawable.abs_back, 1))
            workoutDao.insert(Workout("Lower abs", R.drawable.abs_lower_1, 2))
            workoutDao.insert(Workout("Lower abs", R.drawable.abs_lower_2, 1))
            workoutDao.insert(Workout("Front bend", R.drawable.body_front_bend_, 1))
            workoutDao.insert(Workout("Burpees", R.drawable.burpees, 1))
            workoutDao.insert(Workout("Half burpee", R.drawable.burpees_half, 1))
            workoutDao.insert(Workout("High knees", R.drawable.highknees, 1))
            workoutDao.insert(Workout("Jumping jacks", R.drawable.jumpingjacks, 2))
            workoutDao.insert(Workout("Alternating lunges", R.drawable.lunges_alternating, 2))
            workoutDao.insert(Workout("Chair lunges", R.drawable.lunges_chair_back, 2))
            workoutDao.insert(Workout("Jump lunges", R.drawable.lunges_jump, 2))
            workoutDao.insert(Workout("Kick lunges", R.drawable.lunges_kick, 2))
            workoutDao.insert(Workout("Push ups", R.drawable.pushups, 3))
            workoutDao.insert(Workout("Arm lift push ups", R.drawable.pushup_armlifting, 3))
            workoutDao.insert(Workout("Buttkicks", R.drawable.run_leg_back, 3))
            workoutDao.insert(Workout("Chair squats", R.drawable.squat_chair, 3))
            workoutDao.insert(Workout("Jump squats", R.drawable.squat_jump, 3))
            workoutDao.insert(Workout("Sumo squat", R.drawable.squat_sumo, 3))
            workoutDao.insert(Workout("Sumo lift squats", R.drawable.squat_sumo_lift_leg, 1))
            workoutDao.insert(Workout("Weight rotate", R.drawable.squat_weight_rotate, 1))
            workoutDao.insert(Workout("Wide squats", R.drawable.squat_wide, 1))
            workoutDao.insert(Workout("Chair steps", R.drawable.step_on_chair, 3))
            workoutDao.insert(Workout("Front superman", R.drawable.superman_front_bend, 1))
            workoutDao.insert(Workout("Alternating touches", R.drawable.toe_touch_alternating, 2))

        }
    }
}