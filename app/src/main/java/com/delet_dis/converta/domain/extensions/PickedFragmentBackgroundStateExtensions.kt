package com.delet_dis.converta.domain.extensions

import com.delet_dis.converta.data.model.PickedFragmentBackgroundType

fun findPickedFragmentBackgroundState(searchingFragmentId: Int): PickedFragmentBackgroundType? =
    PickedFragmentBackgroundType.values().find { pickedFragmentBackgroundState ->
        pickedFragmentBackgroundState.fragmentId == searchingFragmentId
    }