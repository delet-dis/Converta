package com.delet_dis.converta.presentation.activities.mainActivity.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.delet_dis.converta.data.database.entities.Category
import com.delet_dis.converta.data.database.entities.Phrase
import com.delet_dis.converta.data.model.ApplicationMainModeType
import com.delet_dis.converta.data.repositories.DatabaseRepository
import com.delet_dis.converta.data.repositories.SharedPreferencesRepository
import com.delet_dis.converta.data.repositories.TextToSpeechEngineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val _preferredApplicationMode =
        MutableLiveData(SharedPreferencesRepository(getApplication()).getMainAppMode())
    val preferredApplicationMode: LiveData<ApplicationMainModeType>
        get() = _preferredApplicationMode

    fun getPreferredApplicationMode() =
        _preferredApplicationMode.postValue(SharedPreferencesRepository(getApplication()).getMainAppMode())

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

    fun deletePhraseInDatabase(phrase: Phrase) =
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepository(getApplication()).deletePhrase(phrase)
        }

    fun deleteCategoryInDatabase(category: Category) =
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepository(getApplication()).deleteCategory(category)
        }

    fun shutdownTTSEngine() =
        TextToSpeechEngineRepository(getApplication()).shutdownEngine()

    fun stopTTSEngine() =
        TextToSpeechEngineRepository(getApplication()).stopEngine()

    fun reInitTTS() {
        TextToSpeechEngineRepository(getApplication()).reInitTTS()
    }
}