package com.delet_dis.converta.presentation.activities.onboardingActivity.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnboardingActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val _isAvailableToNavigateFromHelloFragment = MutableLiveData(false)
    val isAvailableToNavigateFromHelloFragment: LiveData<Boolean>
        get() = _isAvailableToNavigateFromHelloFragment

    fun initNavigateFromHelloFragmentAvailabilityCountdown() {
        viewModelScope.launch {
            delay(1500)
            _isAvailableToNavigateFromHelloFragment.postValue(true)
        }
    }
}