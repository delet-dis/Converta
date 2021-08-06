package com.delet_dis.converta.domain.repositories

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.delet_dis.converta.data.model.STTStateType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class SpeechRecognizerRepository(val context: Context) {
    companion object {
        private var speechRecognizer: SpeechRecognizer? = null

        fun getSpeechRecognizer(context: Context): SpeechRecognizer {
            if (speechRecognizer == null) {
                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            }
            return speechRecognizer!!
        }
    }

    private val _sttState = MutableStateFlow(STTStateType.READY_FOR_SPEECH)
    val sttState: Flow<STTStateType>
        get() = _sttState

    private var _recognizedPhrasesArray = ArrayList<String>()

    private val _recognizedPhrases = MutableStateFlow(_recognizedPhrasesArray)
    val recognizedPhrases: Flow<List<String>>
        get() = _recognizedPhrases

    fun initSTTEngine() = getSpeechRecognizer(context)

    fun startListening() {
        getSpeechRecognizer(context).startListening(createRecognizerIntent())

        initRecognitionListener()
    }

    fun stopListening() =
        getSpeechRecognizer(context).stopListening()

    private fun createRecognizerIntent() =
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                TextToSpeechEngineRepository(context).getDefaultLanguage().language
            )
        }

    private fun initRecognitionListener() =
        getSpeechRecognizer(context).setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                changeState(STTStateType.READY_FOR_SPEECH)
            }

            override fun onBeginningOfSpeech() {
                changeState(STTStateType.BEGINNING_OF_SPEECH)
            }

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {
                changeState(STTStateType.END_OF_SPEECH)
            }

            override fun onError(error: Int) {
                if((error!=7) and (error!=5)){
                    changeState(STTStateType.ERROR)
                }
            }

            override fun onResults(results: Bundle?) {
                changeState(STTStateType.RESULTS)
                getSpeechRecognizer(context).stopListening()
            }

            override fun onPartialResults(partialResults: Bundle?) {
                changeState(STTStateType.PROCESSING_OF_SPEECH)

                val tempArray = ArrayList<String>()

                partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    ?.get(0)?.let {
                        tempArray.add(it)
                    }

                _recognizedPhrasesArray = tempArray

                emitRecognizedPhrases(_recognizedPhrasesArray)
            }

            override fun onEvent(eventType: Int, params: Bundle?) {}

        })

    private fun changeState(state: STTStateType) =
        GlobalScope.launch(Dispatchers.IO) {
            _sttState.emit(state)
        }

    private fun emitRecognizedPhrases(phrases: ArrayList<String>) =
        GlobalScope.launch(Dispatchers.IO) {
            _recognizedPhrases.emit(phrases)
        }
}