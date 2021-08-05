package com.delet_dis.converta.presentation.activities.mainActivity.fragments.sttFragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.delet_dis.converta.R
import com.delet_dis.converta.data.interfaces.FragmentParentInterface
import com.delet_dis.converta.data.model.CardModeType
import com.delet_dis.converta.databinding.FragmentSttBinding
import com.delet_dis.converta.presentation.activities.mainActivity.fragments.sttFragment.viewModel.STTFragmentViewModel

class STTFragment : Fragment(), FragmentParentInterface {
    private lateinit var binding: FragmentSttBinding

    private lateinit var sttFragmentViewModel: STTFragmentViewModel

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            initPickedPhrasesCardViewParameters()

            initListenButtonOnClickListener()

            initSTTStateObserver()

            initRecognizedPhrasesObserver()
        }
    }

    override fun getFragmentId(): Int {
        return R.id.STTFragment
    }

    private fun initPickedPhrasesCardViewParameters() = binding.pickedPhrasesCardView.apply {
        isNewPhrasesHolderDisplayed = false
        cardMode = CardModeType.BLUE
        deleteAllPhrasesFromListOfPicked = { deleteAllPhrasesFromListOfPicked() }
    }

    private fun deleteAllPhrasesFromListOfPicked() =
        sttFragmentViewModel.deleteAllPhrasesFromListOfPicked()

    private fun initSTTStateObserver() =
        sttFragmentViewModel.sttStateLiveData.observe(viewLifecycleOwner, {

        })

    private fun initRecognizedPhrasesObserver() =
        sttFragmentViewModel.recognizedPhrasesLiveData.observe(viewLifecycleOwner, {
            binding.pickedPhrasesCardView.pickedPhrases = it
        })

    private fun initListenButtonOnClickListener() = with(binding) {
        synthesizingButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestMicrophonePermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            } else {
                sttFragmentViewModel.startSTTListening()
            }
        }
    }
}