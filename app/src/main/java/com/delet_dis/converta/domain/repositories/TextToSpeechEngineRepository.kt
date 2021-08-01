package com.delet_dis.converta.domain.repositories

import android.content.Context
import android.speech.tts.TextToSpeech

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

    fun speakString(stringToSpeak: String) {
        getTextToSpeechEngine(context).speak(
            stringToSpeak,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "tts1"
        )
    }
}