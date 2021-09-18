package com.delet_dis.converta.presentation.activities.onboardingActivity.fragments.communicationLanguageChooserFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.delet_dis.converta.databinding.FragmentCommunicationLanguageChooserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunicationLanguageChooserFragment : Fragment() {
    private lateinit var binding: FragmentCommunicationLanguageChooserBinding

    private lateinit var parentActivityCallback: ParentActivityCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = FragmentCommunicationLanguageChooserBinding.inflate(layoutInflater)

            val callback: OnBackPressedCallback =
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        requireActivity().finish()
                    }
                }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

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
            initGoToSynthesizerSettingsButtonOnClickListener()
        }
    }

    private fun initGoToSynthesizerSettingsButtonOnClickListener() =
        with(binding.goToSynthesizerSettings) {
            setOnClickListener {
                parentActivityCallback.callSettingsIntent()
            }
        }

    interface ParentActivityCallback {
        fun callSettingsIntent()
    }
}