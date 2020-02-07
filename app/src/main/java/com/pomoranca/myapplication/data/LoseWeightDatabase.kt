package com.pomoranca.myapplication.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pomoranca.myapplication.R

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
            workoutDao.insert(Workout("Pike push-ups", R.raw.pike_push_ups, 2, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Bicycle crunches", R.raw.bicycle_crunches, 2, "Run or jog in place", "Breathe"))
            workoutDao.insert(Workout("Hip bridges", R.raw.hip_bridges, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Cross pushups", R.raw.cross_pushups, 3, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Sit ups", R.raw.sit_ups, 1, "Keep back straight", "Don't hold your breath"))
            workoutDao.insert(Workout("Side to side sit ups", R.raw.side_to_side_sit_ups, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Scissor kicks", R.raw.scissor_kicks, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Toe touches", R.raw.toe_touches, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Knee to elbow plank", R.raw.knee_to_elbow_plank, 2, "After four reps switch legs", "Don't hold your breath"))
            workoutDao.insert(Workout("Shoulder tap push ups", R.raw.shoulder_tap_push_ups, 2, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Knee push ups", R.raw.knee_push_ups, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Split sit ups", R.raw.split_sit_ups, 2, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Alternating arm plank", R.raw.alternating_arm_plank, 3, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Burpees", R.raw.burpees, 3, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Half burpees", R.raw.half_burpees, 3, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Tucked leg raises", R.raw.tucked_leg_raises, 3, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Narrow to wide push ups", R.raw.narrow_to_wide_push_ups, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Pike push ups", R.raw.pike_push_ups, 2, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Rotating lunges", R.raw.rotating_lunges, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Squats", R.raw.squats, 1, "Keep back straight", "Don't have to go lower than 90 degrees"))
            workoutDao.insert(Workout("Reverse plank", R.raw.reverse_plank, 1, "Keep core tight", "Don't hold your breath"))
            workoutDao.insert(Workout("Russian twists", R.raw.russian_twists, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Around the world", R.raw.around_the_world, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Backward alternating lunges", R.raw.backward_alternating_lunges, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Jumping jacks", R.raw.jumping_jacks, 1, "", "Don't hold your breath"))
            workoutDao.insert(Workout("Butt kicks", R.raw.butt_kicks, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))

        }
    }
}