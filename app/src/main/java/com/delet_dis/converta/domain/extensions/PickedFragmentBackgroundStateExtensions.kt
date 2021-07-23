package com.delet_dis.converta.domain.extensions

import com.delet_dis.converta.data.model.PickedFragmentBackgroundState

fun findPickedFragmentBackgroundState(searchingFragmentId: Int): PickedFragmentBackgroundState? =
    PickedFragmentBackgroundState.values().find { pickedFragmentBackgroundState ->
        pickedFragmentBackgroundState.fragmentId == searchingFragmentId
    }