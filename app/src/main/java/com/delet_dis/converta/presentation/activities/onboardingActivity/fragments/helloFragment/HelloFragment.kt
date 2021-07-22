package com.delet_dis.converta.presentation.activities.onboardingActivity.fragments.helloFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delet_dis.converta.databinding.FragmentHelloBinding
import com.delet_dis.converta.presentation.activities.onboardingActivity.viewModel.OnboardingActivityViewModel

class HelloFragment : Fragment() {
    private lateinit var binding: FragmentHelloBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = FragmentHelloBinding.inflate(layoutInflater)

            binding.root
        } else {
            view
        }
    }

}