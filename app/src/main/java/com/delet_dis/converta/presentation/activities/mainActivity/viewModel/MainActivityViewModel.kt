package com.delet_dis.converta.presentation.activities.mainActivity.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.delet_dis.converta.data.model.ApplicationMainModeType
import com.delet_dis.converta.domain.repositories.SharedPreferencesRepository

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val _preferredApplicationMode =
        MutableLiveData(SharedPreferencesRepository(getApplication()).getMainAppMode())
    val preferredApplicationMode: LiveData<ApplicationMainModeType>
        get() = _preferredApplicationMode

    fun getPreferredApplicationMode() =
        _preferredApplicationMode.postValue(SharedPreferencesRepository(getApplication()).getMainAppMode())
}