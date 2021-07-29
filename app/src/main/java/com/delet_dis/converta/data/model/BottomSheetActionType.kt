package com.delet_dis.converta.data.model

import com.delet_dis.converta.R

enum class BottomSheetActionType(val actionStringId: Int?) {
    CATEGORY_ADDING(R.string.categoryAddingMode),
    CATEGORY_EDITING(R.string.categoryEditingMode),
    CATEGORY_PICKING(null),
    PHRASE_ADDING(R.string.phraseAddingMode),
    PHRASE_EDITING(R.string.phraseEditingMode),
    PHRASE_PICKING(null)
}