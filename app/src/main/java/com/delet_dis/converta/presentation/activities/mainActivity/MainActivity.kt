package com.delet_dis.converta.presentation.activities.mainActivity

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.delet_dis.converta.R
import com.delet_dis.converta.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initActivityBackground()
    }

    private fun initActivityBackground() = with(binding.backgroundImage) {
        background = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(
                getColor(R.color.orange),
                getColor(R.color.gradientSubOrangeOne),
                getColor(R.color.gradientSubOrangeTwo),
                getColor(R.color.gradientSubBlueThree),
                getColor(R.color.gradientSubBlueFour),
                getColor(R.color.blue)
            )
        )
    }
}