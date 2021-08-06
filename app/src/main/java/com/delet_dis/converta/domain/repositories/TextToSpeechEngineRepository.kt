package com.delet_dis.converta.domain.repositories

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.delet_dis.converta.data.model.TTSStateType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*


class TextToSpeechEngineRepository(val context: Context) {
    companion object {
        private var textToSpeechEngine: TextToSpeech? = null

        fun getTextToSpeechEngine(context: Context): TextToSpeech {
            if (textToSpeechEngine == null) {
                textToSpeechEngine = TextToSpeech(
                    context
                ) {}
            }
            return textToSpeechEngine!!
        }
    }

    private val _ttsState = MutableStateFlow(TTSStateType.DONE)
    val ttsStateType: Flow<TTSStateType>
        get() = _ttsState

    fun initTTSEngine() =
        getTextToSpeechEngine(context)

    fun speakString(stringToSpeak: String) {
        val params = Bundle()
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "")

        initTTSUtteranceProgressListener()

        getTextToSpeechEngine(context).speak(
            stringToSpeak,
            TextToSpeech.QUEUE_FLUSH,
            params,
            "tts"
        )
    }

    private fun initTTSUtteranceProgressListener() =
        getTextToSpeechEngine(context).setOnUtteranceProgressListener(object :
            UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                changeState(TTSStateType.START)
            }

            override fun onDone(utteranceId: String?) {
                changeState(TTSStateType.DONE)
            }

            override fun onError(utteranceId: String?) {
                changeState(TTSStateType.ERROR)
            }
        })

    fun stopEngine() =
        getTextToSpeechEngine(context).stop()

    fun shutdownEngine() =
        getTextToSpeechEngine(context).shutdown()

    fun getDefaultLanguage(): Locale =
        getTextToSpeechEngine(context).voice.locale

    private fun changeState(state: TTSStateType) =
        GlobalScope.launch(Dispatchers.IO) {
            _ttsState.emit(state)
        }
}