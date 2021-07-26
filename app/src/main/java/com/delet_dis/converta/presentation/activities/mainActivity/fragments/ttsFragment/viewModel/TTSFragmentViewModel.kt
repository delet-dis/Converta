package com.delet_dis.converta.presentation.activities.mainActivity.fragments.ttsFragment.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.delet_dis.converta.data.database.entities.Phrase
import com.delet_dis.converta.domain.repositories.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TTSFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val _phrasesRecordingsLiveData = MutableLiveData<MutableList<Phrase>>()
    val phrasesRecordingsLiveData: LiveData<MutableList<Phrase>>
        get() = _phrasesRecordingsLiveData

    init {
        loadPhrasesRecordings()
    }

    private fun loadPhrasesRecordings() {
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepository(getApplication()).getPhrases().collect {
                _phrasesRecordingsLiveData.postValue(it.toMutableList())
            }
        }
    }
}