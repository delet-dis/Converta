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

    fun addCategoryToDatabase(category: String) =
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepository(getApplication()).addCategory(
                category
            )
        }

    fun renameCategoryInDatabase(category: Category, newCategoryName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepository(getApplication()).renameCategory(category, newCategoryName)
        }

    fun addPhraseToDatabaseByCategory(category: Category, newPhraseName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepository(getApplication()).addPhraseInCategory(category, newPhraseName)
        }

    fun renamePhraseInDatabase(category: Category, phrase: Phrase, newPhraseName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepository(getApplication()).renamePhrase(category, phrase, newPhraseName)
        }
}