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
            workoutDao.insert(Workout("Pike push-ups", R.raw.pike_push_ups, 2, "Keep your head tucked down", "Don't hold your breath"))
            workoutDao.insert(Workout("Bicycle crunches", R.raw.bicycle_crunches, 2, "Don't hold your breath", "Try not to hit the ground at any time"))
            workoutDao.insert(Workout("Hip bridges", R.raw.hip_bridges, 1, "Keep core tight", "Don't hold your breath"))
            workoutDao.insert(Workout("Climber push-ups", R.raw.climber_push_ups, 3, "Keep core tight", "Don't hold your breath"))
            workoutDao.insert(Workout("Sit ups", R.raw.sit_ups, 1, "Keep core tight", "Don't hold your breath"))
            workoutDao.insert(Workout("Half globes", R.raw.half_globes, 1, "Keep core tight", "Don't hold your breath"))
            workoutDao.insert(Workout("Scissor kicks", R.raw.scissor_kicks, 2, "Keep your legs off the floor", "Don't hold your breath"))
            workoutDao.insert(Workout("Toe touches", R.raw.toe_touches, 1, "Lean all the way back", "Don't hold your breath"))
            workoutDao.insert(Workout("Knee to elbow plank", R.raw.knee_to_elbow_plank, 2, "Alternate legs", "Don't hold your breath"))
            workoutDao.insert(Workout("Shoulder tap push ups", R.raw.shoulder_tap_push_ups, 2, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Knee push ups", R.raw.knee_push_ups, 1, "Keep hips down and body in a straight line", "Exhale as you push up"))
            workoutDao.insert(Workout("Knee tuck sit ups", R.raw.knee_tuck_sit_ups, 2, "Keep your hands on the floor", "Don't hold your breath"))
            workoutDao.insert(Workout("Alternating arm plank", R.raw.alternating_arm_plank, 3, "Keep hip down and body in a straight line", "Don't hold your breath"))
            workoutDao.insert(Workout("Burpees", R.raw.burpees, 3, "Keep your back straight", "Don't hold your breath"))
            workoutDao.insert(Workout("Half burpees", R.raw.half_burpees, 3, "Keep your hips tight", "Don't hold your breath"))
            workoutDao.insert(Workout("Tucked leg raises", R.raw.tucked_leg_raises, 3, "Move up and down as high as you can, without touching the floor", "Don't hold your breath"))
            workoutDao.insert(Workout("Pike push ups", R.raw.pike_push_ups, 2, "Keep your back straight, and head tucked inside", "Don't hold your breath"))
            workoutDao.insert(Workout("Rotating lunges", R.raw.rotating_lunges, 3, "Keep your back straight", "Try to touch floor with your knee"))
            workoutDao.insert(Workout("Squats", R.raw.squats, 1, "Keep back straight", "Don't have to go lower than 90 degrees"))
            workoutDao.insert(Workout("Reverse plank", R.raw.reverse_plank, 1, "Keep core tight", "Don't hold your breath"))
            workoutDao.insert(Workout("Russian twists", R.raw.russian_twists, 1, "Keep core tight", "Don't hold your breath"))
            workoutDao.insert(Workout("Around the worlds", R.raw.around_the_world, 1, "Keep core tight", "Don't hold your breath"))
            workoutDao.insert(Workout("Jumping jacks", R.raw.jumping_jacks, 1, "keep your back straight", "Keep your shoulders back"))
            workoutDao.insert(Workout("Butt kicks", R.raw.butt_kicks, 1, "Keep hands behind and touch with your feet", "Keep back straight"))
            workoutDao.insert(Workout("Narrow to wide push-ups", R.raw.narrow_to_wide_pushups, 3, "Keep core tight", "Don't hold your breath"))
            workoutDao.insert(Workout("Backward  lunges", R.raw.backward_lunges, 3, "Keep you back straight", "Don't hold your breath"))
            workoutDao.insert(Workout("Jumping lunges", R.raw.jump_lunges, 3, "Keep your balance", "Keep your shoulders back"))
            workoutDao.insert(Workout("Side bends", R.raw.side_bends, 1, "Keep your back straight", "Lean deep"))
            workoutDao.insert(Workout("Cross steps", R.raw.cross_steps, 1, "Try not to stop", "Don't hold your breath"))
            workoutDao.insert(Workout("Paper jumps", R.raw.paper_jumps, 1, "Keep heels off ground", "Don't hold your breath"))
            workoutDao.insert(Workout("Hip hops", R.raw.hip_hops, 2, "Keep heels off ground", "Keep your shoulders back"))
            workoutDao.insert(Workout("Forward steps", R.raw.forward_steps, 2, "If you get tired don't stop. Lower your pace", "Lean deep"))
            workoutDao.insert(Workout("Chair steps", R.raw.chair_steps, 3, "Keep heels off ground", "Don't hold your breath"))
            workoutDao.insert(Workout("Elevated pike push-ups", R.raw.elevated_pike_pushups, 3, "Keep you back straight and head tucked inside", "Don't hold your breath"))
            workoutDao.insert(Workout("Bicycle crunches", R.raw.bicycle_crunches, 3, "Try to touch knees with your elbows", "Don't hold your breath"))

        }
    }
}