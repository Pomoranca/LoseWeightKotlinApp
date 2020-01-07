package com.pomoranca.myapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pomoranca.myapplication.R
import kotlinx.android.synthetic.main.activity_award.*

class AwardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_award)

        setSupportActionBar(toolbar_award)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val awardName = intent.getStringExtra("NAME")
        val awardDesc = intent.getStringExtra("DESC")
        val awardBackground = intent.getIntExtra("BACKGROUND", 0)

    }
}
