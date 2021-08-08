package com.delet_dis.converta.presentation.activities.onboardingActivity.fragments.preferredModeChooserFragment.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.delet_dis.converta.data.model.ApplicationMainModeType
import com.delet_dis.converta.data.repositories.SharedPreferencesRepository

class PreferredModeChooserFragmentViewModel(application: Application) :
    AndroidViewModel(application) {

    fun savePickedModeToSharedPreferences(mode: ApplicationMainModeType) =
        SharedPreferencesRepository(getApplication()).setMainAppMode(
            mode
        )

    fun setOnboardingPassedStatus(status: Boolean) =
        SharedPreferencesRepository(getApplication()).setOnboardingPassedStatus(
            status
        )
}