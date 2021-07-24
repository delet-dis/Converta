package com.delet_dis.converta.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["name"], unique = true)])
data class Category(
    @ColumnInfo(name = "name")
    var name: String? = null,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
