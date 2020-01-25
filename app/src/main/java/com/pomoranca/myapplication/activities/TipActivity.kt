package com.pomoranca.myapplication.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.pomoranca.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_tip.*
import kotlinx.android.synthetic.main.activity_tip.toolbar

class TipActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tip)

        val tipBackground = intent.getIntExtra("BACKGROUND", R.drawable.tips_replace_juice)
        val tipTitle = intent.getStringExtra("NAME")
        val tipDescription = intent.getStringExtra("DESCRIPTION")
        Glide
            .with(this)
            .load(tipBackground)
            .centerCrop()
            .into(tip_background)


        tip_description.text = tipDescription
    }
}
