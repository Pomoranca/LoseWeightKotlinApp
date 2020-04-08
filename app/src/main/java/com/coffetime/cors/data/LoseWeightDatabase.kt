package com.coffetime.cors.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.coffetime.cors.R

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
            workoutDao.insert(Workout("Hip bridges", R.raw.hip_bridges, 1, "Keep core tight", "Don't hold your breath"))
            workoutDao.insert(Workout("Half globes", R.raw.half_globes, 1, "Keep core tight", "Don't hold your breath"))
            workoutDao.insert(Workout("Scissor kicks", R.raw.scissor_kicks, 2, "Keep your legs off the floor", "Don't hold your breath"))
            workoutDao.insert(Workout("Burpees", R.raw.burpees, 3, "Keep your back straight", "Don't hold your breath"))
            workoutDao.insert(Workout("Half burpees", R.raw.half_burpees, 3, "Keep your hips tight", "Don't hold your breath"))
            workoutDao.insert(Workout("Squats", R.raw.squats, 2, "Keep back straight", "Don't have to go lower than 90 degrees"))
            workoutDao.insert(Workout("Reverse plank", R.raw.reverse_plank, 1, "Keep core tight", "Don't hold your breath"))
            workoutDao.insert(Workout("Russian twists", R.raw.russian_twists, 2, "Keep core tight", "Don't hold your breath"))
            workoutDao.insert(Workout("Jumping jacks", R.raw.jumping_jacks, 1, "keep your back straight", "Keep your shoulders back"))
            workoutDao.insert(Workout("Butt kicks", R.raw.butt_kicks, 1, "Keep hands behind and touch with your feet", "Keep back straight"))
            workoutDao.insert(Workout("Narrow to wide push-ups", R.raw.narrow_to_wide_pushups, 3, "Keep core tight", "Don't hold your breath"))
            workoutDao.insert(Workout("Jumping lunges", R.raw.jump_lunges, 3, "Keep your balance", "Keep your shoulders back"))
            workoutDao.insert(Workout("Paper jumps", R.raw.paper_jumps, 1, "Keep heels off ground", "Don't hold your breath"))
            workoutDao.insert(Workout("Hip hops", R.raw.hip_hops, 2, "Keep heels off ground", "Keep your shoulders back"))
            workoutDao.insert(Workout("Chair steps", R.raw.chair_steps, 3, "Keep heels off ground", "Don't hold your breath"))
            workoutDao.insert(Workout("Elevated pike push-ups", R.raw.elevated_pike_pushups, 3, "Keep your back straight and head tucked inside", "Don't hold your breath"))
            workoutDao.insert(Workout("Alternating leg curls", R.raw.alternating_leg_curls, 3, "Try not to sit", "Don't forget to breathe"))
            workoutDao.insert(Workout("Alternating lunges", R.raw.alternating_lunges, 2, "Keep your back straight", "Keep core tight"))
            workoutDao.insert(Workout("Back abs", R.raw.back_abs, 1, "Hold up position for one second", "Keep core tight"))
            workoutDao.insert(Workout("Back rolls", R.raw.back_rolls, 2, "Try not to stop", "Don't touch ground with your feet"))
            workoutDao.insert(Workout("Back ups", R.raw.back_ups, 2, "Exhale when going up", "Keep core tight"))
            workoutDao.insert(Workout("Clap push ups", R.raw.clap_push_ups, 3, "Do as many as you can", "Keep core tight"))
            workoutDao.insert(Workout("Cobras", R.raw.cobras, 1, "Hold up position for half a second", "Don't forget to breathe"))
            workoutDao.insert(Workout("Easy russian twists", R.raw.easy_russian_twists, 1, "Keep feet on the ground", "Keep core tight"))
            workoutDao.insert(Workout("Feet to hand jumps", R.raw.feet_to_hand_jumps, 3, "Keep hands in triangle pose", "Jump to your hands"))
            workoutDao.insert(Workout("Frog squats", R.raw.frog_squats, 2, "Keep your back straight", "Touch ground with your hands"))
            workoutDao.insert(Workout("High knees", R.raw.high_knees, 1, "Push knees up as high as you can", "Keep your back straight"))
            workoutDao.insert(Workout("High plank", R.raw.high_plank, 2, "Keep core tight", "Keep your back straight"))
            workoutDao.insert(Workout("Insane jumps", R.raw.insane_jumps, 3, "Don't jump over high objects", "Take your time"))
            workoutDao.insert(Workout("Jump rope", R.raw.jump_rope, 1, "If you don't have rope, just jump", "Don't forget to breathe"))
            workoutDao.insert(Workout("Jump squats", R.raw.jump_squats, 3, "Keep your back straight", "Keep your balance"))
            workoutDao.insert(Workout("Climber push ups", R.raw.climber_push_ups, 3, "Keep your back straight", "Keep core tight"))
            workoutDao.insert(Workout("Mountain climbers", R.raw.mountain_climbers, 2, "Keep your back straight", "Keep core tight"))
            workoutDao.insert(Workout("Plank", R.raw.plank, 2, "Keep your back straight", "Don't forget to breathe"))
            workoutDao.insert(Workout("Push ups", R.raw.push_ups, 2, "Try to keep a good posture", "Keep core tight"))
            workoutDao.insert(Workout("Quick shuffle", R.raw.quick_shuffle, 2, "Shuffle for 2 seconds then slow down", "Don't forget to breathe"))
            workoutDao.insert(Workout("Shadow punches", R.raw.shadow_punches, 1, "Exhale on punch", "Protect your chin"))
            workoutDao.insert(Workout("Side squats", R.raw.side_squats, 3, "Keep your back straight", "Go as low as you can"))
            workoutDao.insert(Workout("Side twists", R.raw.side_twists, 1, "Touch ground with your feet", "Don't forget to breathe"))
            workoutDao.insert(Workout("Sit downs", R.raw.sit_downs, 3, "Keep your back straight", "Keep core tight"))
            workoutDao.insert(Workout("Straight leg sit ups", R.raw.straight_leg_sit_ups, 2, "Keep your legs straight", "Exhale when going up"))



        }
    }
}