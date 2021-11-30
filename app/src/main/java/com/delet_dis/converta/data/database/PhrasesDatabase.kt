package com.delet_dis.converta.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {
        private var INSTANCE: PhrasesDatabase? = null

        fun getAppDatabase(context: Context): PhrasesDatabase {
            if (INSTANCE == null) {
                synchronized(PhrasesDatabase::class) {
                    INSTANCE =
                        Room.databaseBuilder(
                            context.applicationContext,
                            PhrasesDatabase::class.java,
                            "phrasesDB"
                        )
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}