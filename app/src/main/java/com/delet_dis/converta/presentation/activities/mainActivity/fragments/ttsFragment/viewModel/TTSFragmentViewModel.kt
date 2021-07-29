package com.delet_dis.converta.presentation.activities.mainActivity.fragments.ttsFragment.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.delet_dis.converta.data.database.entities.Category
import com.delet_dis.converta.data.database.entities.Phrase
import com.delet_dis.converta.domain.repositories.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TTSFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val _categoriesRecordingsLiveData = MutableLiveData<MutableList<Category>>()
    val categoriesRecordingsLiveData: LiveData<MutableList<Category>>
        get() = _categoriesRecordingsLiveData

    private val _phrasesInCategoryRecordingsLiveData = MutableLiveData<MutableList<Phrase>>()
    val phrasesInCategoryRecordingsLiveData: LiveData<MutableList<Phrase>>
        get() = _phrasesInCategoryRecordingsLiveData

    init {
        loadCategoriesRecordings()
    }

    private fun loadCategoriesRecordings() {
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepository(getApplication()).getCategories().collect {
                _categoriesRecordingsLiveData.postValue(it.toMutableList())
            }
        }
    }

    fun loadPhrasesRecordingsByCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepository(getApplication()).getPhrasesByCategory(category).collect {
                _phrasesInCategoryRecordingsLiveData.postValue(it.toMutableList())
            }
        }
    }
}