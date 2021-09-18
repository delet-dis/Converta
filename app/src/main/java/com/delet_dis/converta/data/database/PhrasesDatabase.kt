package com.delet_dis.converta.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.delet_dis.converta.data.database.daos.CategoryDAO
import com.delet_dis.converta.data.database.daos.PhraseDAO
import com.delet_dis.converta.data.database.entities.Category
import com.delet_dis.converta.data.database.entities.Phrase

@Database(
    entities = [
        Phrase::class,
        Category::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PhrasesDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDAO
    abstract fun phraseDao(): PhraseDAO
}