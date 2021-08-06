package com.delet_dis.converta.data.model

import com.delet_dis.converta.R

enum class SettingsActionType(val stringId: Int, val imageId: Int) {
    COMMUNICATION_LANGUAGE_PICK(
        R.string.changeCommunicationLanguageSettingsAction,
        R.drawable.ic_people
    ),
    APPLICATION_OPEN_MODE_PICK(
        R.string.changeApplicationOpenModeSettingsAction,
        R.drawable.ic_pen
    )
}