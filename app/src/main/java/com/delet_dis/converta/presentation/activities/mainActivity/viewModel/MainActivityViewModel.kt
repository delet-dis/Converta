package com.delet_dis.converta.presentation.activities.mainActivity.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delet_dis.converta.data.database.entities.Category
import com.delet_dis.converta.data.database.entities.Phrase
import com.delet_dis.converta.data.model.ApplicationMainModeType
import com.delet_dis.converta.data.repositories.DatabaseRepository
import com.delet_dis.converta.data.repositories.SharedPreferencesRepository
import com.delet_dis.converta.data.repositories.TextToSpeechEngineRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {
    private val _preferredApplicationMode =
        MutableLiveData(SharedPreferencesRepository(context).getMainAppMode())
    val preferredApplicationMode: LiveData<ApplicationMainModeType>
        get() = _preferredApplicationMode

    fun getPreferredApplicationMode() =
        _preferredApplicationMode.postValue(SharedPreferencesRepository(context).getMainAppMode())

    fun addCategoryToDatabase(category: String) =
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.addCategory(
                category
            )
        }

    fun renameCategoryInDatabase(category: Category, newCategoryName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.renameCategory(category, newCategoryName)
        }

    fun addPhraseToDatabaseByCategory(category: Category, newPhraseName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.addPhraseInCategory(category, newPhraseName)
        }

    fun renamePhraseInDatabase(category: Category, phrase: Phrase, newPhraseName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.renamePhrase(category, phrase, newPhraseName)
        }

    fun deletePhraseInDatabase(phrase: Phrase) =
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.deletePhrase(phrase)
        }

    fun deleteCategoryInDatabase(category: Category) =
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.deleteCategory(category)
        }

    fun shutdownTTSEngine() =
        TextToSpeechEngineRepository(context).shutdownEngine()

    fun stopTTSEngine() =
        TextToSpeechEngineRepository(context).stopEngine()

    fun reInitTTS() {
        TextToSpeechEngineRepository(context).reInitTTS()
    }
}