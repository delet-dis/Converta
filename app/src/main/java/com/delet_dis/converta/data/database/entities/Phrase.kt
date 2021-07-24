package com.delet_dis.converta.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Phrase(
    var name: String? = null,

    var associatedCategoryId: Int? = null,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
