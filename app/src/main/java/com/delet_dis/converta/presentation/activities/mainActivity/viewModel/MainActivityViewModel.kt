package com.delet_dis.converta.presentation.activities.mainActivity.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.delet_dis.converta.data.model.ApplicationMainModeType
import com.delet_dis.converta.domain.repositories.DatabaseRepository
import com.delet_dis.converta.domain.repositories.SharedPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val _preferredApplicationMode =
        MutableLiveData(SharedPreferencesRepository(getApplication()).getMainAppMode())
    val preferredApplicationMode: LiveData<ApplicationMainModeType>
        get() = _preferredApplicationMode

    fun getPreferredApplicationMode() {
        _preferredApplicationMode.postValue(SharedPreferencesRepository(getApplication()).getMainAppMode())
    }

    fun addCategoryToDatabase(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepository(getApplication()).addCategory(
                category
            )
        }
    }
}