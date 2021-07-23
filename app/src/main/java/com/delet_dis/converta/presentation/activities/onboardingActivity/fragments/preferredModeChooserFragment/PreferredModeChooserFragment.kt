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

class PreferredModeChooserFragment: Fragment() {
    private lateinit var binding: FragmentPreferredModeChooserBinding

    private lateinit var parentActivityCallback: ParentActivityCallback

    private var pickedMainMode:ApplicationMainModeType? = null

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

        if(savedInstanceState==null){
            with(binding){
                TTSModeCard.setOnClickListener {
                    parentActivityCallback.backgroundImageGoToOrange()

                    pickedMainMode = ApplicationMainModeType.TTS_MODE

                    rootView.transitionToState(R.id.pickedOrangeModeState)
                }

                SSTModeCard.setOnClickListener {
                    parentActivityCallback.backgroundImageGoToBlue()

                    pickedMainMode = ApplicationMainModeType.SST_MODE

                    rootView.transitionToState(R.id.pickedBlueModeState)
                }
            }
        }
    }

    interface ParentActivityCallback{
        fun backgroundImageGoToOrange()
        fun backgroundImageGoToBlue()
    }
}