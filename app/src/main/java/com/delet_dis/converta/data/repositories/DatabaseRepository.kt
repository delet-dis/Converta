package com.delet_dis.converta.data.repositories

import com.delet_dis.converta.data.database.daos.CategoryDAO
import com.delet_dis.converta.data.database.daos.PhraseDAO
import com.delet_dis.converta.data.database.entities.Category
import com.delet_dis.converta.data.database.entities.Phrase
import com.delet_dis.converta.domain.extensions.beautifyString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class DatabaseRepository @Inject constructor(
    private val phraseDAO: PhraseDAO,
    private val categoryDAO: CategoryDAO
) {
    fun getCategories(): Flow<List<Category>> =
        categoryDAO.getAllCategoriesAsFlow().map {
            it.forEach { category ->
                category.name = category.name?.replaceFirstChar { char ->
                    if (char.isLowerCase()) char.titlecase(
                        Locale.getDefault()
                    ) else char.toString()
                }
            }

            it
        }

    suspend fun renameCategory(category: Category, newCategoryName: String) =
        categoryDAO.insert(Category(newCategoryName.beautifyString(), category.id))

    suspend fun addCategory(category: String) {
        if (category.isNotBlank() and category.isNotEmpty()) {
            categoryDAO.insert(
                Category(
                    category.beautifyString().lowercase(Locale.getDefault())
                )
            )
        }
    }

    fun getPhrasesByCategory(category: Category): Flow<List<Phrase>> =
        phraseDAO.getAllPhrasesByCategory(category.id)

    suspend fun addPhraseInCategory(category: Category, newPhraseName: String) =
        phraseDAO.insert(Phrase(newPhraseName.beautifyString(), category.id))

    suspend fun renamePhrase(category: Category, phrase: Phrase, newPhraseName: String) =
        phraseDAO.insert(
            Phrase(
                newPhraseName.beautifyString(),
                category.id,
                phrase.id
            )
        )

    suspend fun deleteCategory(category: Category) {
        deletePhrasesByCategory(category)
        categoryDAO.deleteById(category.id)
    }

    suspend fun deletePhrase(phrase: Phrase) =
        phraseDAO.deleteById(phrase.id)

    private fun deletePhrasesByCategory(category: Category) {
        getPhrasesByCategory(category).map {
            it.forEach { phrase ->
                deletePhrase(phrase)
            }
        }
    }
}