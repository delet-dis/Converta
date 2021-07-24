package com.delet_dis.converta.presentation.activities.mainActivity.fragments.ttsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delet_dis.converta.R
import com.delet_dis.converta.data.interfaces.FragmentParentInterface
import com.delet_dis.converta.databinding.FragmentTtsBinding

class TTSFragment : Fragment(), FragmentParentInterface {
    private lateinit var binding: FragmentTtsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = FragmentTtsBinding.inflate(layoutInflater)

            binding.root
        } else {
            view
        }
    }

    override fun getFragmentId(): Int {
        return R.id.TTSFragment
    }
}