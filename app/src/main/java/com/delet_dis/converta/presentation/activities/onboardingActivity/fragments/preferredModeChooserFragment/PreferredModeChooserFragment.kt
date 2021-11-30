package com.delet_dis.converta.presentation.activities.onboardingActivity.fragments.preferredModeChooserFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.delet_dis.converta.R
import com.delet_dis.converta.data.model.ApplicationMainModeType
import com.delet_dis.converta.databinding.FragmentPreferredModeChooserBinding
import com.delet_dis.converta.presentation.activities.onboardingActivity.fragments.preferredModeChooserFragment.viewModel.PreferredModeChooserFragmentViewModel

class PreferredModeChooserFragment : Fragment() {
    private lateinit var binding: FragmentPreferredModeChooserBinding

    private lateinit var preferredModeChooserFragmentViewModel: PreferredModeChooserFragmentViewModel

    private lateinit var parentActivityCallback: ParentActivityCallback

    private var pickedMainMode: ApplicationMainModeType? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = FragmentPreferredModeChooserBinding.inflate(layoutInflater)

            preferredModeChooserFragmentViewModel =
                PreferredModeChooserFragmentViewModel(requireActivity().application)

            binding.root
        } else {
            view
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        parentActivityCallback = context as ParentActivityCallback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            initTTSModeCardOnClickListener()

            initSTTModeCardOnClickListener()

            initFinishButtonOnClickListener()
        }
    }

    private fun initFinishButtonOnClickListener() = with(binding.finishButton) {
        setOnClickListener {
            pickedMainMode?.let { pickedMode ->
                with(preferredModeChooserFragmentViewModel) {
                    savePickedModeToSharedPreferences(pickedMode)
                    setOnboardingPassedStatus(true)
                }
            }

            with(requireActivity()) {
                findNavController(R.id.navigationOnboardingControllerContainerView)
                    .navigate(R.id.action_preferredModeChooserFragment_to_mainActivity)

                finish()
            }
        }
    }

    private fun initSTTModeCardOnClickListener() = with(binding) {
        STTModeCard.setOnClickListener {
            parentActivityCallback.backgroundImageGoToBlue()

            pickedMainMode = ApplicationMainModeType.STT_MODE

            rootView.transitionToState(R.id.pickedBlueModeState)
        }
    }

    private fun initTTSModeCardOnClickListener() = with(binding) {
        TTSModeCard.setOnClickListener {
            parentActivityCallback.backgroundImageGoToOrange()

            pickedMainMode = ApplicationMainModeType.TTS_MODE

            rootView.transitionToState(R.id.pickedOrangeModeState)
        }
    }

    interface ParentActivityCallback {
        fun backgroundImageGoToOrange()
        fun backgroundImageGoToBlue()
    }
}