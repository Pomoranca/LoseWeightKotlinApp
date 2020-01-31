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
            workoutDao.insert(Workout("Back abs", R.raw.abs_back, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Cardio", R.raw.abs_lower_1, 2, "Run or jog in place", "Breathe"))
            workoutDao.insert(Workout("Squats", R.raw.abs_lower_2, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Mountain climbers", R.raw.body_front_bend, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Burpees", R.raw.burpees, 1, "Keep back straight", "Don't hold your breath"))
            workoutDao.insert(Workout("Leg raises", R.raw.burpees_half, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("High knees", R.raw.highknees, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Jumping jacks", R.raw.jumpingjacks, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Alternating lunges", R.raw.lunges_alternating, 2, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Scissor kicks", R.raw.lunges_chair_back, 2, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Russian twists", R.raw.lunges_jump, 2, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Plank", R.raw.lunges_kick, 2, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Push ups", R.raw.pushups, 3, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Split jumps", R.raw.pushup_armlifting, 3, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("But kicks", R.raw.run_leg_back, 3, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Plank jack", R.raw.squat_chair, 3, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Jump squats", R.raw.squat_jump, 3, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Lateral toe taps", R.raw.squat_sumo, 3, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Bench runners", R.raw.squat_sumo_lift_leg, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Jump rope", R.raw.squat_weight_rotate, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Wide squats", R.raw.squat_wide, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Fast-feet drop", R.raw.step_on_chair, 3, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Jumping split squat", R.raw.superman_front_bend, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Alternating touches", R.raw.toe_touch_alternating, 2, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Skater jumps", R.raw.step_on_chair, 3, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Windmills", R.raw.superman_front_bend, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Toe touches", R.raw.superman_front_bend, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Plank ups", R.raw.step_on_chair, 3, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Supermans", R.raw.superman_front_bend, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Hip Bridges", R.raw.toe_touch_alternating, 2, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Bicycle crunches", R.raw.step_on_chair, 3, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Wide push-ups", R.raw.superman_front_bend, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("T Rotation", R.raw.superman_front_bend, 1, "Keep hip down and body in a straight line", "Don't hold your breath"))

        }
    }
}