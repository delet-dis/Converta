package com.delet_dis.converta.presentation.activities.onboardingActivity

import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.delet_dis.converta.R
import com.delet_dis.converta.databinding.ActivityOnboardingBinding
import com.delet_dis.converta.presentation.activities.onboardingActivity.viewModel.OnboardingActivityViewModel

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding

    private lateinit var onboardingActivityViewModel: OnboardingActivityViewModel

    private var hostFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnboardingBinding.inflate(layoutInflater)

        onboardingActivityViewModel = OnboardingActivityViewModel(application)

        setContentView(binding.root)

        initActivityBackground()

        with(onboardingActivityViewModel){
            initNavigateFromHelloFragmentAvailabilityCountdown()
            isAvailableToNavigateFromHelloFragment.observe(this@OnboardingActivity, {
                if (it){

                }
            })
        }

    }

    private fun initActivityBackground() {
        val backgroundGradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TL_BR, intArrayOf(
                getColor(R.color.orange),
                getColor(R.color.gradientSubOrangeOne),
                getColor(R.color.gradientSubOrangeTwo),
                getColor(R.color.gradientSubBlueThree),
                getColor(R.color.gradientSubBlueFour),
                getColor(R.color.blue)
            )
        )

        binding.backgroundImage.background = backgroundGradientDrawable
    }
}