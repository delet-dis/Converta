package com.delet_dis.converta.domain.extensions

import android.content.Context
import com.delet_dis.converta.domain.repositories.SharedPreferencesRepository

fun isOnboardingPassed(context: Context): Boolean =
    SharedPreferencesRepository(context).getOnboardingPassedStatus()
