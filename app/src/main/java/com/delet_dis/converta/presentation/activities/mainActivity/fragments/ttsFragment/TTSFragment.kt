package com.delet_dis.converta.presentation.activities.mainActivity.fragments.ttsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.delet_dis.converta.R
import com.delet_dis.converta.data.interfaces.FragmentParentInterface
import com.delet_dis.converta.databinding.FragmentTtsBinding
import com.delet_dis.converta.presentation.activities.mainActivity.fragments.ttsFragment.viewModel.TTSFragmentViewModel
import com.delet_dis.converta.presentation.activities.mainActivity.recyclerViewAdapters.PhrasesPickingAdapter

class TTSFragment : Fragment(), FragmentParentInterface {
    private lateinit var binding: FragmentTtsBinding

    private lateinit var ttsFragmentViewModel: TTSFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = FragmentTtsBinding.inflate(layoutInflater)

            ttsFragmentViewModel = TTSFragmentViewModel(requireActivity().application)

            binding.root
        } else {
            view
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBottomListRecycler()

        displayPhrasesRecordings()
    }

    private fun initBottomListRecycler() = with(binding) {
        itemsBottomRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun displayPhrasesRecordings() {
        ttsFragmentViewModel.phrasesRecordingsLiveData.observe(viewLifecycleOwner, {
            binding.itemsBottomRecycler.adapter = PhrasesPickingAdapter(it) {
                Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun getFragmentId(): Int {
        return R.id.TTSFragment
    }
}