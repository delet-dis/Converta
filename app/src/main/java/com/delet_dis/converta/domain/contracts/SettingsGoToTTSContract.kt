package com.delet_dis.converta.domain.contracts

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class SettingsGoToTTSContract : ActivityResultContract<Int, Boolean>() {
    override fun createIntent(context: Context, input: Int?): Intent {
        return Intent().apply {
            action = "com.android.settings.TTS_SETTINGS"
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean = true

}