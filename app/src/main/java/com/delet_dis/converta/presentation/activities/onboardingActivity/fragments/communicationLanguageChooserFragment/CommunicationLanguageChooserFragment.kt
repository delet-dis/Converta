package com.delet_dis.converta.presentation.activities.onboardingActivity.fragments.communicationLanguageChooserFragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.delet_dis.converta.databinding.FragmentCommunicationLanguageChooserBinding


class CommunicationLanguageChooserFragment : Fragment() {
    private lateinit var binding: FragmentCommunicationLanguageChooserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return if (savedInstanceState == null) {
            binding = FragmentCommunicationLanguageChooserBinding.inflate(layoutInflater)

            binding.root
        } else {
            view
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            binding.goToSynthesizerSettings.setOnClickListener {
                startActivity(Intent().setAction("com.android.settings.TTS_SETTINGS"))
            }
        }
    }

}