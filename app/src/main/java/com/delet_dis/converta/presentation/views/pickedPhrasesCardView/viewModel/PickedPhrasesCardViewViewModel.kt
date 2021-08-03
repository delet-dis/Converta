package com.delet_dis.converta.presentation.views.pickedPhrasesCardView.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.delet_dis.converta.domain.repositories.TextToSpeechEngineRepository

class PickedPhrasesCardViewViewModel(application: Application) : AndroidViewModel(application) {
    fun stopTTSEngine() = TextToSpeechEngineRepository(getApplication()).stopEngine()
}