package com.delet_dis.converta.data.repositories

import android.content.Context
import android.content.SharedPreferences
import com.delet_dis.converta.data.model.ApplicationMainModeType

class SharedPreferencesRepository(private val context: Context) {
    private fun getSharedPreferences(): SharedPreferences =
        context.getSharedPreferences(
            appSettings,
            Context.MODE_PRIVATE
        )

    fun setMainAppMode(modeType: ApplicationMainModeType) {
        val valueToPut: Int = when (modeType) {
            ApplicationMainModeType.TTS_MODE -> 1
            ApplicationMainModeType.STT_MODE -> 2
        }

        getSharedPreferences().edit()
            .putInt(
                pickedAppMode,
                valueToPut
            )
            .apply()
    }

    fun getMainAppMode(): ApplicationMainModeType {
        return when (getSharedPreferences().getInt(pickedAppMode, 1)) {
            1 -> ApplicationMainModeType.TTS_MODE
            2 -> ApplicationMainModeType.STT_MODE
            else -> ApplicationMainModeType.TTS_MODE
        }
    }

    fun getOnboardingPassedStatus(): Boolean =
        getSharedPreferences().getBoolean(onboardingPassedStatus, false)

    fun setOnboardingPassedStatus(status: Boolean) {
        getSharedPreferences().edit()
            .putBoolean(
                onboardingPassedStatus,
                status
            )
            .apply()
    }

    companion object SharedPreferencesConstantsRepository {
        const val appSettings = "APP_SETTINGS"
        const val pickedAppMode = "PICKED_APP_MODE"
        const val onboardingPassedStatus = "ONBOARDING_STATUS"
    }
}