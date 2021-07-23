package com.delet_dis.converta.data.model

import com.delet_dis.converta.R

enum class PickedFragmentBackgroundType(val backgroundStateToNavigate: Int, val fragmentId: Int) {
    HELLO_FRAGMENT(R.id.backgroundImageRotateStart, R.id.helloFragment),
    COMMUNICATION_LANGUAGE_CHOOSER_FRAGMENT(
        R.id.backgroundImageRotateEnd,
        R.id.communicationLanguageChooserFragment
    )
}