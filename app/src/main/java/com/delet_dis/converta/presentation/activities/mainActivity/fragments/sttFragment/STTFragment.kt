package com.delet_dis.converta.presentation.activities.mainActivity.fragments.sttFragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.delet_dis.converta.R
import com.delet_dis.converta.data.interfaces.FragmentParentInterface
import com.delet_dis.converta.data.model.ColorModeType
import com.delet_dis.converta.data.model.STTStateType
import com.delet_dis.converta.databinding.FragmentSttBinding
import com.delet_dis.converta.presentation.activities.mainActivity.fragments.sttFragment.viewModel.STTFragmentViewModel

class STTFragment : Fragment(), FragmentParentInterface {
    private lateinit var binding: FragmentSttBinding

    private lateinit var sttFragmentViewModel: STTFragmentViewModel

    private lateinit var parentActivityCallback: ParentActivityCallback

    private val requestMicrophonePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            sttFragmentViewModel.startSTTListening()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = FragmentSttBinding.inflate(layoutInflater)

            sttFragmentViewModel = STTFragmentViewModel(requireActivity().application)

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
            initPickedPhrasesCardViewParameters()

            initSTTStateObserver()

            initRecognizedPhrasesObserver()

            initSettingsButtonOnClick()
        }
    }

    override fun getFragmentId(): Int {
        return R.id.STTFragment
    }

    private fun initPickedPhrasesCardViewParameters() = binding.pickedPhrasesCardView.apply {
        isNewPhrasesHolderDisplayed = false
        colorMode = ColorModeType.BLUE
        deleteAllPhrasesFromListOfPicked = { deleteAllPhrasesFromListOfPicked() }
    }

    private fun deleteAllPhrasesFromListOfPicked() =
        sttFragmentViewModel.deleteAllPhrasesFromListOfPicked()

    private fun initSTTStateObserver() =
        sttFragmentViewModel.sttStateLiveData.observe(viewLifecycleOwner, {
            when (it) {
                STTStateType.READY_FOR_SPEECH -> setSTTReadyForSpeech()

                STTStateType.BEGINNING_OF_SPEECH -> setSTTProcessingOfSpeech()

                STTStateType.PROCESSING_OF_SPEECH -> setSTTProcessingOfSpeech()

                STTStateType.END_OF_SPEECH -> setSTTReadyForSpeech()

                STTStateType.RESULTS -> setSTTReadyForSpeech()

                STTStateType.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        requireContext().getString(R.string.ttsErrorText),
                        Toast.LENGTH_LONG
                    ).show()

                    setSTTReadyForSpeech()
                }

                else -> {
                }
            }
        })

    private fun initSettingsButtonOnClick() {
        binding.settingsButton.setOnClickListener {
            parentActivityCallback.displaySettingsBottomSheet(ColorModeType.BLUE)
        }
    }

    private fun initRecognizedPhrasesObserver() =
        sttFragmentViewModel.recognizedPhrasesLiveData.observe(viewLifecycleOwner, {
            binding.pickedPhrasesCardView.pickedPhrases = it
        })

    private fun setSTTReadyForSpeech() = binding.synthesizingButton.apply {
        text = context.getString(R.string.startSTTRecognition)

        setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestMicrophonePermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            } else {
                sttFragmentViewModel.startSTTListening()
            }

            text = context.getString(R.string.listening)
        }
    }

    private fun setSTTProcessingOfSpeech() = binding.synthesizingButton.apply {
        text = context.getString(R.string.stopSTTRecognition)

        setOnClickListener {
            sttFragmentViewModel.stopSTTListening()
            setSTTReadyForSpeech()
        }
    }

    interface ParentActivityCallback {
        fun displaySettingsBottomSheet(colorModeType: ColorModeType)
    }
}