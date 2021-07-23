package com.delet_dis.converta.presentation.activities.onboardingActivity.fragments.preferredModeChooserFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delet_dis.converta.R
import com.delet_dis.converta.data.model.ApplicationMainModeType
import com.delet_dis.converta.databinding.FragmentPreferredModeChooserBinding
import com.delet_dis.converta.domain.repositories.SharedPreferencesRepository

class PreferredModeChooserFragment : Fragment() {
    private lateinit var binding: FragmentPreferredModeChooserBinding

    private lateinit var parentActivityCallback: ParentActivityCallback

    private var pickedMainMode: ApplicationMainModeType? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = FragmentPreferredModeChooserBinding.inflate(layoutInflater)

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
            with(binding) {
                initTTSModeCardOnClickListener()

                initSSTModeCardOnClickListener()

                initFinishButtonOnClickListener()
            }
        }
    }

    private fun initFinishButtonOnClickListener() {
        binding.finishButton.setOnClickListener {
            pickedMainMode?.let { it1 ->
                SharedPreferencesRepository(requireContext()).setMainAppMode(
                    it1
                )
            }
        }
    }

    private fun FragmentPreferredModeChooserBinding.initSSTModeCardOnClickListener() {
        SSTModeCard.setOnClickListener {
            parentActivityCallback.backgroundImageGoToBlue()

            pickedMainMode = ApplicationMainModeType.SST_MODE

            rootView.transitionToState(R.id.pickedBlueModeState)
        }
    }

    private fun FragmentPreferredModeChooserBinding.initTTSModeCardOnClickListener() {
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