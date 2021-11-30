package com.delet_dis.converta.domain.extensions

import com.delet_dis.converta.data.model.SettingsActionType

fun findSettingsAction(searchingAction: String): SettingsActionType? =
    SettingsActionType.values().find { settingsActionType ->
        settingsActionType.name == searchingAction
    }