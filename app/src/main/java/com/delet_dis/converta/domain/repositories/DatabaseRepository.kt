package com.delet_dis.converta.domain.repositories

import android.content.Context
import com.delet_dis.converta.data.database.PhrasesDatabase
import com.delet_dis.converta.data.database.daos.CategoryDAO
import com.delet_dis.converta.data.database.daos.PhraseDAO
import com.delet_dis.converta.data.database.entities.Category
import com.delet_dis.converta.domain.extensions.beautifyString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

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

    fun getCategories(): Flow<List<Category>> =
        getCategoryDao(context).getAllCategoriesAsFlow().map {
            it.forEach { category ->
                category.name = category.name?.replaceFirstChar { char ->
                    if (char.isLowerCase()) char.titlecase(
                        Locale.getDefault()
                    ) else char.toString()
                }
            }

            it
        }

    suspend fun renameCategory(category: Category, newCategoryName: String) {
        getCategoryDao(context).insert(Category(newCategoryName, category.id))
    }

    suspend fun addCategory(category: String) {
        if (category.isNotBlank() and category.isNotEmpty()) {
            getCategoryDao(context).insert(
                Category(
                    category.beautifyString().lowercase(Locale.getDefault())
                )
            )
        }
    }

}