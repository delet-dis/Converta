package com.delet_dis.converta.domain.repositories

import android.content.Context
import com.delet_dis.converta.data.database.PhrasesDatabase
import com.delet_dis.converta.data.database.daos.CategoryDAO
import com.delet_dis.converta.data.database.daos.PhraseDAO
import com.delet_dis.converta.data.database.entities.Phrase
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(val context: Context) {
    companion object {
        private var categoryDAO: CategoryDAO? = null
        fun getCategoryDao(context: Context): CategoryDAO {
            if (categoryDAO == null) {
                synchronized(CategoryDAO::class) {
                    categoryDAO = PhrasesDatabase.getAppDatabase(context).categoryDao()
                }
            }
            return categoryDAO!!
        }

        private var phraseDAO: PhraseDAO? = null
        fun getPhraseDao(context: Context): PhraseDAO {
            if (phraseDAO == null) {
                synchronized(CategoryDAO::class) {
                    phraseDAO = PhrasesDatabase.getAppDatabase(context).phraseDao()
                }
            }
            return phraseDAO!!
        }
    }

    fun getPhrases(): Flow<List<Phrase>> {
        return getPhraseDao(context).getAllPhrasesAsFlow()
    }

}