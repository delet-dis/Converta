package com.delet_dis.converta.presentation.activities.mainActivity.fragments.sttFragment.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.delet_dis.converta.data.database.entities.Phrase
import com.delet_dis.converta.data.model.STTStateType
import com.delet_dis.converta.domain.repositories.SpeechRecognizerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class STTFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val _sttStateLiveData = MutableLiveData(STTStateType.READY_FOR_SPEECH)
    val sttStateLiveData: LiveData<STTStateType>
        get() = _sttStateLiveData

    private val _recognizedPhrasesLiveData = MutableLiveData<ArrayList<Phrase>>()
    val recognizedPhrasesLiveData: LiveData<ArrayList<Phrase>>
        get() = _recognizedPhrasesLiveData

    private val sttRepository = SpeechRecognizerRepository(application)


    init {
        initSTTEngine()
        collectSTTState()
        collectRecognizedPhrases()
    }

    private fun initSTTEngine() = sttRepository.initSTTEngine()

    fun startSTTListening() = sttRepository.startListening()

    fun stopSTTListening() = sttRepository.stopListening()

    private fun collectSTTState() = viewModelScope.launch(Dispatchers.IO) {
        sttRepository.sttState.collect {
            _sttStateLiveData.postValue(it)
        }
    }

    private fun collectRecognizedPhrases() = viewModelScope.launch(Dispatchers.IO) {
        sttRepository.recognizedPhrases.collect { list ->
            val arrayListOfPhrases = ArrayList<Phrase>()

            list.filter {
                it.count() > 0
            }

            list.forEach { string ->
                arrayListOfPhrases.add(Phrase((string)))
            }

            _recognizedPhrasesLiveData.postValue(arrayListOfPhrases)
        }
    }

    fun deleteAllPhrasesFromListOfPicked() = viewModelScope.launch(Dispatchers.IO) {
        _recognizedPhrasesLiveData.postValue(ArrayList())
    }
}