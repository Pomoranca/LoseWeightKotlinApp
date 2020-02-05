package com.pomoranca.myapplication.activities

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.pomoranca.myapplication.R
import kotlinx.android.synthetic.main.activity_tip.*

class TipActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
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
        tip_textView_title.text = tipTitle
    }
}
