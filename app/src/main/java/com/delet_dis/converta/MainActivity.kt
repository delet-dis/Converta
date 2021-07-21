package com.delet_dis.converta

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.delet_dis.converta.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        var gradientDrawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(
            getColor(R.color.orange),
            getColor(R.color.gradientSubOrangeOne),
            getColor(R.color.gradientSubOrangeTwo),
            getColor(R.color.gradientSubBlueThree),
            getColor(R.color.gradientSubBlueFour),
            getColor(R.color.blue)
        ))

        binding.constraint.background = gradientDrawable
    }
}