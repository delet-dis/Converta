package com.delet_dis.converta.presentation.activities.onboardingActivity.fragments.communicationLanguageChooserFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delet_dis.converta.databinding.FragmentCommunicationLanguageChooserBinding

class CommunicationLanguageChooserFragment:Fragment() {
    private lateinit var binding: FragmentCommunicationLanguageChooserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = FragmentCommunicationLanguageChooserBinding.inflate(layoutInflater)

            binding.root
        } else {
            view
        }
    }
}