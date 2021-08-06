package com.delet_dis.converta.presentation.activities.onboardingActivity

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.delet_dis.converta.R
import com.delet_dis.converta.data.model.SettingsActionType
import com.delet_dis.converta.databinding.ActivityOnboardingBinding
import com.delet_dis.converta.domain.contracts.SettingsGoToTTSContract
import com.delet_dis.converta.domain.extensions.findPickedFragmentBackgroundState
import com.delet_dis.converta.domain.extensions.findSettingsAction
import com.delet_dis.converta.domain.extensions.isOnboardingPassed
import com.delet_dis.converta.domain.repositories.ConstantsRepository
import com.delet_dis.converta.presentation.activities.mainActivity.MainActivity
import com.delet_dis.converta.presentation.activities.onboardingActivity.fragments.communicationLanguageChooserFragment.CommunicationLanguageChooserFragment
import com.delet_dis.converta.presentation.activities.onboardingActivity.fragments.preferredModeChooserFragment.PreferredModeChooserFragment
import com.delet_dis.converta.presentation.activities.onboardingActivity.viewModel.OnboardingActivityViewModel

class OnboardingActivity : AppCompatActivity(),
    CommunicationLanguageChooserFragment.ParentActivityCallback,
    PreferredModeChooserFragment.ParentActivityCallback {
    private lateinit var binding: ActivityOnboardingBinding

    private lateinit var onboardingActivityViewModel: OnboardingActivityViewModel

    private var hostFragment: Fragment? = null

    private var settingsContract = registerForActivityResult(SettingsGoToTTSContract()) {
        if (it) {
            hostFragment?.findNavController()
                ?.navigate(
                    R.id.action_communicationLanguageChooserFragment_to_preferredModeChooserFragment
                )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnboardingBinding.inflate(layoutInflater)

        onboardingActivityViewModel = OnboardingActivityViewModel(application)

        hostFragment =
            supportFragmentManager
                .findFragmentById(binding.navigationOnboardingControllerContainerView.id)

        setContentView(binding.root)

        initActivityBackground()

        initBackgroundChangerListener()

        if (intent.extras?.getString(ConstantsRepository.SCREEN_TO_NAVIGATE) != null) {
            navigateToIntentExtrasFragment()
        } else {
            checkIfOnboardingPassed()

            initNavigationToCommunicationLanguageChooserFragment()
        }
    }

    private fun navigateToIntentExtrasFragment() = with(hostFragment?.findNavController()) {
        when (intent.extras!!.getString(ConstantsRepository.SCREEN_TO_NAVIGATE)?.let {
            findSettingsAction(it)
        }) {
            SettingsActionType.APPLICATION_OPEN_MODE_PICK -> this
                ?.navigate(R.id.action_helloFragment_to_preferredModeChooserFragment)
            else -> {
            }
        }
    }

    private fun checkIfOnboardingPassed() {
        if (isOnboardingPassed(applicationContext)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun initBackgroundChangerListener() =
        hostFragment?.findNavController()?.addOnDestinationChangedListener { _, destination, _ ->
            findPickedFragmentBackgroundState(destination.id)?.let {
                binding.rootLayout.transitionToState(it.backgroundStateToNavigate)
            }
        }

    private fun initNavigationToCommunicationLanguageChooserFragment() =
        with(onboardingActivityViewModel) {
            initNavigateFromHelloFragmentAvailabilityCountdown()
            isAvailableToNavigateFromHelloFragment.observe(this@OnboardingActivity, {
                if (it) {
                    hostFragment?.findNavController()
                        ?.navigate(R.id.action_helloFragment_to_communicationLanguageChooser)
                }
            })
        }

    private fun initActivityBackground() = with(binding.backgroundImage) {
        background = GradientDrawable(
            GradientDrawable.Orientation.TL_BR, intArrayOf(
                getColor(R.color.orange),
                getColor(R.color.gradientSubOrangeOne),
                getColor(R.color.gradientSubOrangeTwo),
                getColor(R.color.gradientSubBlueThree),
                getColor(R.color.gradientSubBlueFour),
                getColor(R.color.blue)
            )
        )
    }

    override fun callSettingsIntent() =
        settingsContract.launch(1)


    override fun backgroundImageGoToOrange() =
        binding.rootLayout.transitionToState(R.id.backgroundImageInOnboardingGoToOrange)


    override fun backgroundImageGoToBlue() =
        binding.rootLayout.transitionToState(R.id.backgroundImageInOnboardingGoToBlue)

    override fun onBackPressed() {
        if(isOnboardingPassed(applicationContext)){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else{
            super.onBackPressed()
        }
    }
}