package com.delet_dis.converta.presentation.activities.mainActivity

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.delet_dis.converta.R
import com.delet_dis.converta.data.model.ApplicationMainModeType
import com.delet_dis.converta.databinding.ActivityMainBinding
import com.delet_dis.converta.presentation.activities.mainActivity.viewModel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        mainActivityViewModel = MainActivityViewModel(application)

        setContentView(binding.root)

        initActivityBackground()

        initBottomNavigationViewOnClickListener()

        updatePreferredApplicationMode()

        initPreferredApplicationModeObserver()
    }

    private fun initPreferredApplicationModeObserver() =
        with(mainActivityViewModel.preferredApplicationMode) {
            observe(
                this@MainActivity, {
                    when (it.name) {
                        ApplicationMainModeType.TTS_MODE.name -> {
                            translationToOrangeState()
                            makeBottomNavigationViewActiveTTSButton()
                        }
                        ApplicationMainModeType.STT_MODE.name -> {
                            translationToBlueState()
                            makeBottomNavigationViewActiveSTTButton()
                        }
                    }
                }
            )
        }

    private fun updatePreferredApplicationMode() =
        mainActivityViewModel.getPreferredApplicationMode()


    private fun initBottomNavigationViewOnClickListener() = with(binding.bottomNavigationView) {
        setOnItemSelectedListener {
            when (it.itemId) {
                R.id.TTSButton -> {
                    translationToOrangeState()
                }
                R.id.STTButton -> {
                    translationToBlueState()
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun translationToBlueState(): Boolean {
        binding.rootView.transitionToState(R.id.backgroundImageInMainGoToBlue)

        return true
    }

    private fun translationToOrangeState(): Boolean {
        binding.rootView.transitionToState(R.id.backgroundImageInMainGoToOrange)

        return true
    }

    private fun makeBottomNavigationViewActiveSTTButton() {
        binding.bottomNavigationView.selectedItemId = R.id.STTButton
    }

    private fun makeBottomNavigationViewActiveTTSButton() {
        binding.bottomNavigationView.selectedItemId = R.id.TTSButton
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